package de.mkienitz.bachelorarbeit.backend4frontend.application.cart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/data/cart")
public interface CartServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{shoppingCartId}")
    Response getShoppingCart(@PathParam("shoppingCartId") String shoppingCartId);
}
