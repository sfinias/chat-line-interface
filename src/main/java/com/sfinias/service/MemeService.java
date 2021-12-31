package com.sfinias.service;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.sfinias.model.MemeModel;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/gimme")
@RegisterRestClient
public interface MemeService {

    @GET
//    @Path("/{subreddit}")
    @Produces(APPLICATION_JSON)
    MemeModel getRandomMeme();
}
