package com.sfinias.resource;

import com.sfinias.model.ProjectModel;
import com.sfinias.service.TogglService;
import io.quarkus.logging.Log;
import java.util.Base64;
import java.util.List;
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

        String encryptedToken = "Basic " + Base64.getEncoder().encodeToString((token + ":api_token").getBytes());
        List<ProjectModel> projects = togglService.getProjectsFromWorkspace(wid, encryptedToken);
        Log.debug("Projects Received: " + projects);
        return projects;
    }

    @GET
    @Path("/lprojects")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectModel> getLunatechProjects() {

        return getProjectsFromWorkspace(94268);
    }
}
