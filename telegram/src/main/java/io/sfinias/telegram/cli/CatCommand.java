package io.sfinias.telegram.cli;

import io.sfinias.cat.resource.CatResource;
import io.sfinias.telegram.SigmaFiBot.ResponseType;
import io.sfinias.telegram.dto.SigmaFiBotResponse;
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

        String url = (isGif ? catResource.getRandomCatGif() : catResource.getRandomCat()).getUrl();
        ResponseType type = isGif ? ResponseType.VIDEO : ResponseType.IMAGE;
        return new SigmaFiBotResponse(type, url);
    }
}
