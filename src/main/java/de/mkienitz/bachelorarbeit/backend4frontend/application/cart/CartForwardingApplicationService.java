package de.mkienitz.bachelorarbeit.backend4frontend.application.cart;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class CartForwardingApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartForwardingApplicationService.class.getName());

    @Inject
    private CartServiceClient client;

    @Traced(operationName = "CartForwardingApplicationService.getShoppingCart")
    public Response getShoppingCart(String shoppingCartId) {
        try {
            Response getShoppingCartResponse = client.getShoppingCart(shoppingCartId);

            LOGGER.info("getShoppingCart(): status = " + getShoppingCartResponse.getStatus());

            return getShoppingCartResponse;
        } catch(WebApplicationException e) {
            LOGGER.warn("getShoppingCart(): an unknown error occurred, e = ", e);

            final String errMsg = "Anfrage zum Warenkorbdienst war nicht erfolgreich, Fehler = " + e.getMessage();

            return Response.status(Response.Status.BAD_GATEWAY).entity(errMsg).build();
        }
    }
}
