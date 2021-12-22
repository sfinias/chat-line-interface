package com.sfinias.service;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.sfinias.model.CatModel;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/images/search")
@RegisterRestClient
public interface CatService {


    @GET
    @Produces(APPLICATION_JSON)
    List<CatModel> getRandomCat(@QueryParam("mime_types")String type);
}
