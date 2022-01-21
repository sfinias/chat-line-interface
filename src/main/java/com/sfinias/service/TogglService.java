package com.sfinias.service;

import com.sfinias.model.ProjectModel;
import com.sfinias.model.RequestTimeEntryModel;
import com.sfinias.model.ResponseTimeEntryModel;
import com.sfinias.model.TimeEntryModel;
import java.time.Instant;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@RegisterRestClient
public interface TogglService {

    @GET
    @Path("/workspaces/{wid}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<ProjectModel> getProjectsFromWorkspace(@PathParam long wid, @HeaderParam("authorization") String token);

    @GET
    @Path("/time_entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<TimeEntryModel> getTimeEntries(@HeaderParam("authorization") String token, @QueryParam("start_date") Instant startDate, @QueryParam("end_date") Instant endDate);

    @POST
    @Path("/time_entries")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    ResponseTimeEntryModel createTimeEntry(@HeaderParam("authorization") String token, RequestTimeEntryModel timeEntryModel);
}
