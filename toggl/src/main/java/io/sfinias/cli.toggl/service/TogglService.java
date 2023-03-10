package io.sfinias.cli.toggl.service;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

import io.sfinias.cli.toggl.model.RequestTimeEntryModel;
import io.sfinias.cli.toggl.model.TogglProjectModel;
import io.sfinias.cli.toggl.model.TogglTimeEntryModel;
import java.time.LocalDate;
import java.util.List;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v9")
@Singleton
@RegisterRestClient(configKey = "toggl-api")
public interface TogglService {

    @GET
    @Path("/workspaces/{workspace_id}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<TogglProjectModel> getProjectsFromWorkspace(@HeaderParam(AUTHORIZATION) String token, @PathParam("workspace_id") long workspaceId);

    @GET
    @Path("/me/projects")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<TogglProjectModel> getPersonalProjects(@HeaderParam(AUTHORIZATION) String token);

    @GET
    @Path("/me/time_entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<TogglTimeEntryModel> getTimeEntries(@HeaderParam(AUTHORIZATION) String token, @QueryParam("start_date") LocalDate startDate, @QueryParam("end_date") LocalDate endDate);

    @POST
    @Path("/workspaces/{workspace_id}/time_entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    TogglTimeEntryModel createTimeEntry(@HeaderParam(AUTHORIZATION) String token, @PathParam("workspace_id") long workspaceId, RequestTimeEntryModel timeEntryModel);
}
