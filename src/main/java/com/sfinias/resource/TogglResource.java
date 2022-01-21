package com.sfinias.resource;

import com.sfinias.model.ProjectModel;
import com.sfinias.model.TimeEntryModel;
import com.sfinias.service.TogglService;
import io.quarkus.logging.Log;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    @Path("/time")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<LocalDate, List<TimeEntryModel>> getTimeEntries() {

        List<TimeEntryModel> timeEntries = togglService.getTimeEntries(encryptedToken(), null, null);
        return timeEntries.stream()
                .collect(Collectors.toMap((TimeEntryModel timeEntryModel) -> LocalDateTime.ofInstant(timeEntryModel.getStop(), ZoneId.systemDefault()).toLocalDate(),
                        List::of, (x, y) -> Stream.of(x, y).flatMap(Collection::stream).collect(Collectors.toList()),
                        () -> new TreeMap<>(Comparator.reverseOrder())));
    }

    private String encryptedToken() {

        return "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
    }
}
