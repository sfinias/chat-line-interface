package com.sfinias.resource;

import com.sfinias.dto.TogglTimeEntry;
import com.sfinias.model.RequestTimeEntryModel;
import com.sfinias.model.TogglProjectModel;
import com.sfinias.model.TogglTimeEntryModel;
import com.sfinias.service.TogglService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.tuples.Tuple2;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/toggl")
public class TogglResource {

    @RestClient
    TogglService togglService;

    @ConfigProperty(name = "toggl.token")
    String token;
    @ConfigProperty(name = "toggl.wid")
    long wid;

    @GET
    @Path("/projects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglProjectModel> getProjectsFromWorkspace(@QueryParam("wid") long wid) {

        List<TogglProjectModel> projects = togglService.getProjectsFromWorkspace(encryptedToken(), wid);
        Log.debug("Projects Received: " + projects);
        return projects;
    }

    @GET
    @Path("/lprojects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglProjectModel> getLunatechProjects() {

        return getProjectsFromWorkspace(wid);
    }

    @GET
    @Path("/time")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<LocalDate, List<TogglTimeEntryModel>> getTimeEntries() {

        List<TogglTimeEntryModel> timeEntries = togglService.getTimeEntries(encryptedToken(), null, null);
        return timeEntries.stream()
                .collect(Collectors.toMap((TogglTimeEntryModel timeEntryModel) -> LocalDateTime.ofInstant(timeEntryModel.getStop(), ZoneId.systemDefault()).toLocalDate(),
                        List::of, (x, y) -> Stream.of(x, y).flatMap(Collection::stream).collect(Collectors.toList()),
                        () -> new TreeMap<>(Comparator.reverseOrder())));
    }

    @GET
    @Path("/time/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglTimeEntryModel> getTimeEntriesOfDate(@PathParam("date") LocalDate date) {

        return togglService.getTimeEntries(encryptedToken(), date, date.plus(Period.ofDays(1)));
    }

    public TogglTimeEntryModel createNewEntry(RequestTimeEntryModel newTimeEntry) {

        return togglService.createTimeEntry(encryptedToken(), newTimeEntry.getWorkspaceId(), newTimeEntry);
    }

    @GET
    @Path("/copy_entry/{dateToBeCopied}/{targetDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglTimeEntry> copyTimeEntriesOfDate(@PathParam("dateToBeCopied") String dateToBeCopied, @PathParam("targetDate") String targetDate) {

        return copyTimeEntriesOfDate(LocalDate.parse(dateToBeCopied), LocalDate.parse(targetDate));
    }

    private List<TogglTimeEntry> copyTimeEntriesOfDate(LocalDate dateToBeCopied, LocalDate targetDate) {

        List<TogglTimeEntryModel> timeEntries = getTimeEntriesOfDate(dateToBeCopied);
        return timeEntries.stream()
                .map(timeEntry -> {
                    RequestTimeEntryModel newTimeEntry = new RequestTimeEntryModel();
                    newTimeEntry.setDescription(timeEntry.getDescription());
                    newTimeEntry.setWorkspaceId(timeEntry.getWorkspaceId());
                    newTimeEntry.setDuration(timeEntry.getDuration());
                    newTimeEntry.setBillable(timeEntry.isBillable());
                    newTimeEntry.setStart(ZonedDateTime.of(targetDate, ZonedDateTime.ofInstant(timeEntry.getStart(), ZoneId.systemDefault()).toLocalTime(), ZoneId.systemDefault()).toInstant());
                    newTimeEntry.setProjectId(timeEntry.getProjectId());
                    newTimeEntry.setCreatedWith("SigmaFiBot");
                    return newTimeEntry;
                })
                .map(newTimeEntry -> togglService.createTimeEntry(encryptedToken(), newTimeEntry.getWorkspaceId(), newTimeEntry))
                .map(timeEntryResponse -> Tuple2.of(timeEntryResponse, getProjectsFromWorkspace(timeEntryResponse.getWorkspaceId()).stream().filter(project -> project.getId() == timeEntryResponse.getProjectId()).findFirst().orElse(null)))
                .map(response -> new TogglTimeEntry(response.getItem1().getDescription(), Optional.ofNullable(response.getItem2()).map(TogglProjectModel::getName).orElse(null), response.getItem1().getStart().atZone(ZoneId.systemDefault()), response.getItem1().getStop().atZone(ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

    private String encryptedToken() {

        return "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
    }
}
