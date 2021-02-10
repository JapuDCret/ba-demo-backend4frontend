package de.mkienitz.bachelorarbeit.backend4frontend.application.cart;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cart")
@RequestScoped
public class CartForwardingResource {

    private static final Logger log = LoggerFactory.getLogger(CartForwardingResource.class.getName());

    @Inject
    private CartServiceClient cartServiceClient;

    @GET
    @Traced(operationName = "CartForwardingResource.getShoppingCart")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{shoppingCartId}")
    public Response getShoppingCart(@PathParam("shoppingCartId") String shoppingCartId) {
        try {
            Response getShoppingCartResponse = cartServiceClient.getShoppingCart(shoppingCartId);

            log.info("getShoppingCart(): status = " + getShoppingCartResponse.getStatus());

            return getShoppingCartResponse;
        } catch(Exception e) {
            log.warn("getShoppingCart(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
