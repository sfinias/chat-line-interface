package com.sfinias.resource;

import com.sfinias.model.CatModel;
import com.sfinias.service.CatService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/cat")
public class CatResource {

    @RestClient
    CatService catService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CatModel getRandomCat() {

        return catService.getRandomCat(null).get(0);
    }

    @GET
    @Path("/gif")
    @Produces(MediaType.APPLICATION_JSON)
    public CatModel getRandomCatGif() {

        return catService.getRandomCat("gif").get(0);
    }
}