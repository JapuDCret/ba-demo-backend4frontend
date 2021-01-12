package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RegisterRestClient
@Path("/data/cart")
public interface CartServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{shoppingCartId}")
    Response getShoppingCart(@PathParam("shoppingCartId") String shoppingCartId);
}
