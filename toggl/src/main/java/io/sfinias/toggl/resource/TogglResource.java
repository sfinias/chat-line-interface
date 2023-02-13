package io.sfinias.toggl.resource;

import io.quarkus.logging.Log;
import io.sfinias.toggl.dto.TogglTimeEntry;
import io.sfinias.toggl.model.RequestTimeEntryModel;
import io.sfinias.toggl.model.TogglCreateNewEntry;
import io.sfinias.toggl.model.TogglProjectModel;
import io.sfinias.toggl.model.TogglTimeEntryModel;
import io.sfinias.toggl.service.TogglService;
import io.smallrye.mutiny.tuples.Tuple2;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TogglResource {

    @RestClient
    TogglService togglService;

    @ConfigProperty(name = "toggl.token")
    String token;

    public List<TogglProjectModel> getProjects(Long wid) {

        List<TogglProjectModel> projects = (wid == null ? togglService.getPersonalProjects(encryptedToken()) : togglService.getProjectsFromWorkspace(encryptedToken(), wid));
        Log.debug("Projects Received: " + projects);
        return projects;
    }

    public List<TogglTimeEntryModel> getTimeEntriesOfDate(LocalDate date) {

        return togglService.getTimeEntries(encryptedToken(), date, date.plus(Period.ofDays(1)));
    }

    public TogglTimeEntry createNewEntry(TogglCreateNewEntry createNewEntry) {

        return getProjects(null).stream()
                .filter(project -> project.getName().toUpperCase().contains(createNewEntry.getProject().toUpperCase()))
                .reduce((a, b) -> {
                    throw new IllegalArgumentException("Multiple projects found with name " + createNewEntry.getProject() + ":\n " + a.getName() + "\n" + b.getName());
                })
                .map(project -> {
                    RequestTimeEntryModel newTimeEntry = new RequestTimeEntryModel();
                    newTimeEntry.setDescription(createNewEntry.getDescription());
                    newTimeEntry.setWorkspaceId(project.getWorkspaceId());
                    newTimeEntry.setStart(createNewEntry.getStart().atZone(ZoneId.systemDefault()).toInstant());
                    newTimeEntry.setStop(createNewEntry.getEnd().atZone(ZoneId.systemDefault()).toInstant());
                    newTimeEntry.setBillable(project.getBillable());
                    newTimeEntry.setProjectId(project.getId());
                    newTimeEntry.setCreatedWith("SigmaFiBot");
                    return Tuple2.of(project, newTimeEntry);
                })
                .map(tuple -> tuple.mapItem2(this::createEntry))
                .map(tuple -> new TogglTimeEntry(tuple.getItem2().getDescription(), tuple.getItem1().getName(), tuple.getItem2().getStart().atZone(ZoneId.systemDefault()), tuple.getItem2().getStop().atZone(ZoneId.systemDefault())))
                .orElseThrow(() -> new RuntimeException("Project Not Found"));
    }

    public List<TogglTimeEntry> copyTimeEntriesOfDate(LocalDate dateToBeCopied, LocalDate targetDate) {

        Map<Integer, TogglProjectModel> projectsMap = getProjects(null).stream().collect(Collectors.toMap(TogglProjectModel::getId, x -> x));
        return getTimeEntriesOfDate(dateToBeCopied).stream()
                .map(timeEntry -> {
                    TogglProjectModel project = projectsMap.get(timeEntry.getProjectId());
                    RequestTimeEntryModel newTimeEntry = new RequestTimeEntryModel();
                    newTimeEntry.setDescription(timeEntry.getDescription());
                    newTimeEntry.setWorkspaceId(timeEntry.getWorkspaceId());
                    newTimeEntry.setDuration(timeEntry.getDuration());
                    newTimeEntry.setBillable(timeEntry.isBillable());
                    newTimeEntry.setStart(ZonedDateTime.of(targetDate, ZonedDateTime.ofInstant(timeEntry.getStart(), ZoneId.systemDefault()).toLocalTime(), ZoneId.systemDefault()).toInstant());
                    newTimeEntry.setProjectId(timeEntry.getProjectId());
                    newTimeEntry.setCreatedWith("SigmaFiBot");
                    return Tuple2.of(project, newTimeEntry);
                })
                .map(tuple -> tuple.mapItem2(this::createEntry))
                .map(tuple -> new TogglTimeEntry(tuple.getItem2().getDescription(), tuple.getItem1().getName(), tuple.getItem2().getStart().atZone(ZoneId.systemDefault()), tuple.getItem2().getStop().atZone(ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

    private TogglTimeEntryModel createEntry(RequestTimeEntryModel timeEntryModel) {

        return togglService.createTimeEntry(encryptedToken(), timeEntryModel.getWorkspaceId(), timeEntryModel);
    }

    private String encryptedToken() {

        return "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
    }
}
