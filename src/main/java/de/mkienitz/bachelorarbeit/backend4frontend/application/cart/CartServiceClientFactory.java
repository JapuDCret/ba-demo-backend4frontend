package de.mkienitz.bachelorarbeit.backend4frontend.application.cart;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.net.MalformedURLException;
import java.net.URL;

public class CartServiceClientFactory {

    private static final Logger log = LoggerFactory.getLogger(CartServiceClientFactory.class.getName());

    @Produces
    public CartServiceClient createClient() throws MalformedURLException {
        String cartServiceUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_CART_SERVICE_URL);

        log.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_CART_SERVICE_URL, cartServiceUrl));

        log.debug("createClient(): creating CartServiceClient");

        URL cartServiceUrl2 = new URL(cartServiceUrl);

        CartServiceClient cartServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(cartServiceUrl2)
                .build(CartServiceClient.class);

        log.debug("createClient(): successfully created CartServiceClient");

        return cartServiceClient;
    }
}
