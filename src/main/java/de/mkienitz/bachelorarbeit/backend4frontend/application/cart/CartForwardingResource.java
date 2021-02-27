package de.mkienitz.bachelorarbeit.backend4frontend.application.cart;

import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cart")
@RequestScoped
public class CartForwardingResource {

    @Inject
    private CartForwardingApplicationService service;

    @GET
    @Traced(operationName = "CartForwardingResource.forwardGetShoppingCart")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{shoppingCartId}")
    public Response forwardGetShoppingCart(@PathParam("shoppingCartId") String shoppingCartId) {
        Response shoppingCartResponse = service.getShoppingCart(shoppingCartId);

        return shoppingCartResponse;
    }
}
