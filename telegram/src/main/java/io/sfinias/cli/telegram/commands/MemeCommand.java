package io.sfinias.cli.telegram.commands;

import io.sfinias.cli.meme.MemeResource;
import io.sfinias.cli.telegram.SigmaFiBot.ResponseType;
import io.sfinias.cli.telegram.dto.SigmaFiBotResponse;
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

        String url = (dank ? memeResource.getRandomDankMeme() : memeResource.getRandomMeme()).url;
        return new SigmaFiBotResponse(ResponseType.IMAGE, url);
    }
}
