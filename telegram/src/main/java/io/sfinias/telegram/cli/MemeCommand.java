package io.sfinias.telegram.cli;

import io.sfinias.meme.resource.MemeResource;
import io.sfinias.telegram.SigmaFiBot.ResponseType;
import io.sfinias.telegram.dto.SigmaFiBotResponse;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "meme", mixinStandardHelpOptions = true, version = "meme 1.0.0",
        description = "Returns memes")
public class MemeCommand implements Callable<SigmaFiBotResponse> {

    private final MemeResource memeResource;

    @Option(names = {"-d", "--dank"}, description = "Returns a dank meme")
    private boolean dank;

    public MemeCommand(MemeResource memeResource) {

        this.memeResource = memeResource;
    }

    @Override
    public SigmaFiBotResponse call() {

        String url = (dank ? memeResource.getRandomDankMeme() : memeResource.getRandomMeme()).getUrl();
        return new SigmaFiBotResponse(ResponseType.IMAGE, url);
    }
}
