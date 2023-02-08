package com.sfinias.resource;

import com.sfinias.dto.TogglTimeEntry;
import com.sfinias.model.RequestTimeEntryModel;
import com.sfinias.model.TogglCreateNewEntry;
import com.sfinias.model.TogglProjectModel;
import com.sfinias.model.TogglTimeEntryModel;
import com.sfinias.service.TogglService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.tuples.Tuple2;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    @GET
    @Path("/projects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglProjectModel> getProjects(@QueryParam("wid") Long wid) {

        List<TogglProjectModel> projects = (wid == null ? togglService.getPersonalProjects(encryptedToken()) : togglService.getProjectsFromWorkspace(encryptedToken(), wid));
        Log.debug("Projects Received: " + projects);
        return projects;
    }

    @GET
    @Path("/entry/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglTimeEntryModel> getTimeEntriesOfDate(@PathParam("date") LocalDate date) {

        return togglService.getTimeEntries(encryptedToken(), date, date.plus(Period.ofDays(1)));
    }

    @POST
    @Path("/entry")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TogglTimeEntry createNewEntry(@Valid TogglCreateNewEntry createNewEntry) {

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

    @POST
    @Path("/entry/copy/{dateToBeCopied}/{targetDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TogglTimeEntry> copyTimeEntriesOfDate(@PathParam("dateToBeCopied") LocalDate dateToBeCopied, @PathParam("targetDate") LocalDate targetDate) {

        return getTimeEntriesOfDate(dateToBeCopied).stream()
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
                .map(timeEntryResponse -> Tuple2.of(timeEntryResponse, getProjects(null).stream().filter(project -> project.getId() == timeEntryResponse.getProjectId()).findFirst().orElse(null)))
                .map(response -> new TogglTimeEntry(response.getItem1().getDescription(), Optional.ofNullable(response.getItem2()).map(TogglProjectModel::getName).orElse(null), response.getItem1().getStart().atZone(ZoneId.systemDefault()), response.getItem1().getStop().atZone(ZoneId.systemDefault())))
                .collect(Collectors.toList());
    }

    private TogglTimeEntryModel createEntry(RequestTimeEntryModel timeEntryModel) {

        return togglService.createTimeEntry(encryptedToken(), timeEntryModel.getWorkspaceId(), timeEntryModel);
    }

    private String encryptedToken() {

        return "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
    }
}
