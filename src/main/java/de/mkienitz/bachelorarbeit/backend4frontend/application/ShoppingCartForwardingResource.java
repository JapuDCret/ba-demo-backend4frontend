package de.mkienitz.bachelorarbeit.backend4frontend.application;

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
public class ShoppingCartForwardingResource {

    public static final String SYS_ENV_VAR_SHOPPINGCART_SERVICE_URL = "BA_SHOPPINGCART_SERVICE_URL";

    private static Logger log = LoggerFactory.getLogger(ShoppingCartForwardingResource.class.getName());

    private ShoppingCartServiceClient shoppingCartServiceClient;

    public ShoppingCartForwardingResource() throws MalformedURLException {
        String shoppingCartServiceUrl = System.getenv(SYS_ENV_VAR_SHOPPINGCART_SERVICE_URL);

        log.info(String.format("ShoppingCartForwardingResource(): env.%s = %s", SYS_ENV_VAR_SHOPPINGCART_SERVICE_URL, shoppingCartServiceUrl));

        log.debug("ShoppingCartForwardingResource(): creating ShoppingCartServiceClient");

        URL shoppingCartServiceUrl2 = new URL(shoppingCartServiceUrl);

        ShoppingCartServiceClient shoppingCartServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(shoppingCartServiceUrl2)
                .build(ShoppingCartServiceClient.class);

        log.debug("ShoppingCartForwardingResource(): successfully created ShoppingCartServiceClient");

        this.shoppingCartServiceClient = shoppingCartServiceClient;
    }

    @GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    @Path("/{shoppingCartId}")
    public Response getShoppingCart(@PathParam("shoppingCartId") String shoppingCartId) {
        try {
            Response getShoppingCartResponse = shoppingCartServiceClient.getShoppingCart(shoppingCartId);

            log.info("getShoppingCart(): status = " + getShoppingCartResponse.getStatus());

            return getShoppingCartResponse;
        } catch(Exception e) {
            log.warn("getShoppingCart(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
