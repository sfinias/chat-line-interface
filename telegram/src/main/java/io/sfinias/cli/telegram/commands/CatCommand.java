package io.sfinias.cli.telegram.commands;

import io.sfinias.cli.cat.CatResource;
import io.sfinias.cli.telegram.SigmaFiBot.ResponseType;
import io.sfinias.cli.telegram.dto.SigmaFiBotResponse;
import java.util.concurrent.Callable;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "cat", mixinStandardHelpOptions = true, version = "cat 1.0.0",
        description = "Returns cat pics")
public class CatCommand implements Callable<SigmaFiBotResponse> {

    private final CatResource catResource;

    @Option(names = {"-g", "--gif"}, description = "Returns a gif")
    private boolean isGif;

    public CatCommand(CatResource catResource) {

        this.catResource = catResource;
    }

    @Override
    public SigmaFiBotResponse call() {

        String url = (isGif ? catResource.getRandomCatGif() : catResource.getRandomCat()).url;
        ResponseType type = isGif ? ResponseType.VIDEO : ResponseType.IMAGE;
        return new SigmaFiBotResponse(type, url);
    }
}
