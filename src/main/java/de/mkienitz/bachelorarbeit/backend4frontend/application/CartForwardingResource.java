package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
@Path("/cart")
@Singleton
public class CartForwardingResource {

    public static final String SYS_ENV_VAR_CART_SERVICE_URL = "BA_CART_SERVICE_URL";

    private static Logger log = LoggerFactory.getLogger(CartForwardingResource.class.getName());

    private CartServiceClient cartServiceClient;

    public CartForwardingResource() throws MalformedURLException {
        String cartServiceUrl = System.getenv(SYS_ENV_VAR_CART_SERVICE_URL);

        log.info(String.format("CartForwardingResource(): env.%s = %s", SYS_ENV_VAR_CART_SERVICE_URL, cartServiceUrl));

        log.debug("CartForwardingResource(): creating CartServiceClient");

        URL cartServiceUrl2 = new URL(cartServiceUrl);

        CartServiceClient cartServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(cartServiceUrl2)
                .build(CartServiceClient.class);

        log.debug("CartForwardingResource(): successfully created CartServiceClient");

        this.cartServiceClient = cartServiceClient;
    }

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
