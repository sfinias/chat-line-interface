package com.sfinias.service;

import com.sfinias.model.RequestTimeEntryModel;
import com.sfinias.model.TogglProjectModel;
import com.sfinias.model.TogglTimeEntryModel;
import java.time.LocalDate;
import java.util.List;
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

@RegisterRestClient
public interface TogglService {

    @GET
    @Path("/workspaces/{workspace_id}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<TogglProjectModel> getProjectsFromWorkspace(@HeaderParam("authorization") String token, @PathParam("workspace_id") long workspaceId);

    @GET
    @Path("/me/time_entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<TogglTimeEntryModel> getTimeEntries(@HeaderParam("authorization") String token, @QueryParam("start_date") LocalDate startDate, @QueryParam("end_date") LocalDate endDate);

    @POST
    @Path("/workspaces/{workspace_id}/time_entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    TogglTimeEntryModel createTimeEntry(@HeaderParam("authorization") String token, @PathParam("workspace_id") long workspaceId, RequestTimeEntryModel timeEntryModel);
}
