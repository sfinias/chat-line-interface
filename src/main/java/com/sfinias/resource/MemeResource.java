package com.sfinias.resource;

import com.sfinias.model.MemeModel;
import com.sfinias.service.MemeService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/meme")
public class MemeResource {

    @RestClient
    MemeService memeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MemeModel getRandomMeme() {

        return memeService.getRandomMeme();
    }
}
