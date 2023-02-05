package com.sfinias.resource;

import com.sfinias.model.ProjectModel;
import com.sfinias.model.RequestTimeEntryModel;
import com.sfinias.model.ResponseTimeEntryModel;
import com.sfinias.model.TimeEntryModel;
import com.sfinias.service.TogglService;
import io.quarkus.logging.Log;
import java.time.Instant;
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
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

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
    public List<ProjectModel> getProjectsFromWorkspace(@QueryParam long wid) {

        List<ProjectModel> projects = togglService.getProjectsFromWorkspace(wid, encryptedToken());
        Log.debug("Projects Received: " + projects);
        return projects;
    }

    @GET
    @Path("/lprojects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectModel> getLunatechProjects() {

        return getProjectsFromWorkspace(wid);
    }

    @GET
    @Path("/lprojects/main")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectModel> getMainLunatechProjects() {

        return getLunatechProjects().stream().filter(ProjectModel::isMain).collect(Collectors.toList());
    }

    @GET
    @Path("/time")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<LocalDate, List<TimeEntryModel>> getTimeEntries() {

        List<TimeEntryModel> timeEntries = togglService.getTimeEntries(encryptedToken(), null, null);
        return timeEntries.stream()
                .collect(Collectors.toMap((TimeEntryModel timeEntryModel) -> LocalDateTime.ofInstant(timeEntryModel.getStop(), ZoneId.systemDefault()).toLocalDate(),
                        List::of, (x, y) -> Stream.of(x, y).flatMap(Collection::stream).collect(Collectors.toList()),
                        () -> new TreeMap<>(Comparator.reverseOrder())));
    }

    @GET
    @Path("/time/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TimeEntryModel> getTimeEntriesOfDate(@PathParam String date) {

        return getTimeEntriesOfDate(LocalDate.parse(date));
    }

    public List<TimeEntryModel> getTimeEntriesOfDate(LocalDate date) {

        Instant start = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return togglService.getTimeEntries(encryptedToken(), start, start.plus(Period.ofDays(1)));
    }

    public ResponseTimeEntryModel createNewEntry(TimeEntryModel newTimeEntry) {

        RequestTimeEntryModel requestTimeEntryModel = new RequestTimeEntryModel();
        requestTimeEntryModel.setTimeEntry(newTimeEntry);
        return togglService.createTimeEntry(encryptedToken(), requestTimeEntryModel);
    }

    @GET
    @Path("/copy_entry/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ResponseTimeEntryModel> copyTimeEntriesOfDate(@PathParam String date) {

        return copyTimeEntriesOfDate(LocalDate.parse(date));
    }

    private List<ResponseTimeEntryModel> copyTimeEntriesOfDate(LocalDate date) {

        List<TimeEntryModel> timeEntries = getTimeEntriesOfDate(date);
        return timeEntries.stream()
                .map(timeEntry -> {
                    TimeEntryModel newTimeEntry = new TimeEntryModel();
                    newTimeEntry.setDescription(timeEntry.getDescription());
                    newTimeEntry.setDuration(timeEntry.getDuration());
                    newTimeEntry.setBillable(timeEntry.isBillable());
                    newTimeEntry.setStart(ZonedDateTime.of(LocalDate.now(), ZonedDateTime.ofInstant(timeEntry.getStart(), ZoneId.systemDefault()).toLocalTime(), ZoneId.systemDefault()).toInstant());
                    newTimeEntry.setPid(timeEntry.getPid());
                    newTimeEntry.setCreatedWith("SigmaFiBot");
                    RequestTimeEntryModel requestTimeEntryModel = new RequestTimeEntryModel();
                    requestTimeEntryModel.setTimeEntry(newTimeEntry);
                    return requestTimeEntryModel;
                })
                .map(newTimeEntry -> togglService.createTimeEntry(encryptedToken(), newTimeEntry))
                .collect(Collectors.toList());
    }

    private String encryptedToken() {

        return "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
    }
}
