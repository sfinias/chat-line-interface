package com.sfinias.service;

import com.sfinias.model.ProjectModel;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@RegisterRestClient
public interface TogglService {

    @GET
    @Path("/workspaces/{wid}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    List<ProjectModel> getProjectsFromWorkspace(@PathParam long wid, @HeaderParam("authorization") String token);
}
