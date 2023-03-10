package io.sfinias.cli.toggl;

import io.quarkus.logging.Log;
import io.sfinias.cli.toggl.dto.TogglCreateNewEntry;
import io.sfinias.cli.toggl.dto.TogglTimeEntry;
import io.sfinias.cli.toggl.model.RequestTimeEntryModel;
import io.sfinias.cli.toggl.model.TogglProjectModel;
import io.sfinias.cli.toggl.model.TogglTimeEntryModel;
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
                .filter(project -> project.name.toUpperCase().contains(createNewEntry.project.toUpperCase()))
                .reduce((a, b) -> {
                    throw new IllegalArgumentException("Multiple projects found with name " + createNewEntry.project + ":\n " + a.name + "\n" + b.name);
                })
                .map(project -> {
                    RequestTimeEntryModel newTimeEntry = new RequestTimeEntryModel();
                    newTimeEntry.description = createNewEntry.description;
                    newTimeEntry.workspaceId = project.workspaceId;
                    newTimeEntry.start = createNewEntry.start.atZone(ZoneId.systemDefault()).toInstant();
                    newTimeEntry.stop = createNewEntry.end.atZone(ZoneId.systemDefault()).toInstant();
                    newTimeEntry.billable = project.billable;
                    newTimeEntry.projectId = project.id;
                    newTimeEntry.createdWith = "SigmaFiBot";
                    return Tuple2.of(project, newTimeEntry);
                })
                .map(tuple -> tuple.mapItem2(this::createEntry))
                .map(tuple -> new TogglTimeEntry(tuple.getItem2().description, tuple.getItem1().name, tuple.getItem2().start.atZone(ZoneId.systemDefault()), tuple.getItem2().stop.atZone(ZoneId.systemDefault())))
                .orElseThrow(() -> new RuntimeException("Project Not Found"));
    }

    public List<TogglTimeEntry> copyTimeEntriesOfDate(LocalDate dateToBeCopied, LocalDate targetDate) {

        Map<Integer, TogglProjectModel> projectsMap = getProjects(null).stream().collect(Collectors.toMap(project -> project.id, x -> x));
        return getTimeEntriesOfDate(dateToBeCopied).stream()
                .map(timeEntry -> {
                    TogglProjectModel project = projectsMap.get(timeEntry.projectId);
                    RequestTimeEntryModel newTimeEntry = new RequestTimeEntryModel();
                    newTimeEntry.description = timeEntry.description;
                    newTimeEntry.workspaceId = timeEntry.workspaceId;
                    newTimeEntry.duration = timeEntry.duration;
                    newTimeEntry.billable = timeEntry.billable;
                    newTimeEntry.start = ZonedDateTime.of(targetDate, ZonedDateTime.ofInstant(timeEntry.start, ZoneId.systemDefault()).toLocalTime(), ZoneId.systemDefault()).toInstant();
                    newTimeEntry.projectId = timeEntry.projectId;
                    newTimeEntry.createdWith = "SigmaFiBot";
                    return Tuple2.of(project, newTimeEntry);
                })
                .map(tuple -> tuple.mapItem2(this::createEntry))
                .map(tuple -> new TogglTimeEntry(tuple.getItem2().description, tuple.getItem1().name, tuple.getItem2().start.atZone(ZoneId.systemDefault()), tuple.getItem2().stop.atZone(ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

    private TogglTimeEntryModel createEntry(RequestTimeEntryModel timeEntryModel) {

        return togglService.createTimeEntry(encryptedToken(), timeEntryModel.workspaceId, timeEntryModel);
    }

    private String encryptedToken() {

        return "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
    }
}