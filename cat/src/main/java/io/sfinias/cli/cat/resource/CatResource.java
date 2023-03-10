package io.sfinias.cli.cat.resource;

import io.sfinias.cli.cat.model.CatModel;
import io.sfinias.cli.cat.service.CatService;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CatResource {

    @RestClient
    CatService catService;

    public CatModel getRandomCat() {

        return catService.getRandomCat(null).get(0);
    }

    public CatModel getRandomCatGif() {

        return catService.getRandomCat("gif").get(0);
    }
}