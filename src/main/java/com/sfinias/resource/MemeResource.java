package com.sfinias.resource;

import com.sfinias.model.MemeModel;
import com.sfinias.service.MemeService;
import io.quarkus.logging.Log;
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

        MemeModel randomMeme = memeService.getRandomMeme();
        Log.debug("Meme Received: " + randomMeme);
        return randomMeme;
    }

    @GET
    @Path("/dank")
    @Produces(MediaType.APPLICATION_JSON)
    public MemeModel getRandomDankMeme() {

        return memeService.getRandomMemeFromSubreddit("dankmemes");
    }
}
