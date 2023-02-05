package com.sfinias.cli;

import com.sfinias.resource.CatResource;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "cat", mixinStandardHelpOptions = true, version = "cat 1.0.0",
        description = "Returns cat pics")
public class CatCommand implements Callable<String> {

    private final CatResource catResource;

    @Option(names = {"-g", "--gif"}, description = "Returns a gif")
    private boolean isGif;

    public CatCommand(CatResource catResource) {

        this.catResource = catResource;
    }

    @Override
    public String call() {

        return isGif ? catResource.getRandomCatGif().getUrl() : catResource.getRandomCat().getUrl();
    }
}
