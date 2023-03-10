package io.sfinias.cli.meme.resource;

import io.quarkus.logging.Log;
import io.sfinias.cli.meme.model.MemeModel;
import io.sfinias.cli.meme.service.MemeService;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class MemeResource {

    @RestClient
    MemeService memeService;

    public MemeModel getRandomMeme() {

        MemeModel randomMeme = memeService.getRandomMeme();
        Log.debug("Meme Received: " + randomMeme);
        return randomMeme;
    }

    public MemeModel getRandomDankMeme() {

        return memeService.getRandomMemeFromSubreddit("dankmemes");
    }
}
