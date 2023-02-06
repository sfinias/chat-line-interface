package com.sfinias.cli;

import com.sfinias.resource.CatResource;
import com.sfinias.resource.MemeResource;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "meme", mixinStandardHelpOptions = true, version = "meme 1.0.0",
        description = "Returns memes")
public class MemeCommand implements Callable<String> {

    private final MemeResource memeResource;

    @Option(names = {"-d", "--dank"}, description = "Returns a dank meme")
    private boolean dank;

    public MemeCommand(MemeResource memeResource) {

        this.memeResource = memeResource;
    }

    @Override
    public String call() {

        return (dank ? memeResource.getRandomDankMeme() : memeResource.getRandomMeme()).getUrl();
    }
}
