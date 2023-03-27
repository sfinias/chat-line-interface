package io.sfinias.cli.cat;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import io.sfinias.cli.cat.model.CatModel;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/v1")
@RegisterRestClient(configKey = "cat-api")
interface CatService {

    @GET
    @Path("/images/search")
    @Produces(APPLICATION_JSON)
    List<CatModel> getRandomCat(@QueryParam("mime_types") String type);
}
