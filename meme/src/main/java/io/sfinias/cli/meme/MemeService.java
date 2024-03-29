package io.sfinias.cli.meme;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.sfinias.cli.meme.model.MemeModel;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/gimme")
@RegisterRestClient(configKey = "meme-api")
interface MemeService {

    @GET
    @Produces(APPLICATION_JSON)
    MemeModel getRandomMeme();

    @GET
    @Path("/{subreddit}")
    @Produces(APPLICATION_JSON)
    MemeModel getRandomMemeFromSubreddit(@PathParam("subreddit") String subreddit);
}
