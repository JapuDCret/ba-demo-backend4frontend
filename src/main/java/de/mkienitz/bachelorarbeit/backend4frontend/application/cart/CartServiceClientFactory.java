package de.mkienitz.bachelorarbeit.backend4frontend.application.cart;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URI;

// can't use ApplicationScoped directly on class, since it's a CDI-managed Producer
public class CartServiceClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartServiceClientFactory.class.getName());

    @javax.enterprise.inject.Produces
    @ApplicationScoped
    public CartServiceClient createClient() throws MalformedURLException {
        URI cartServiceUri = URI.create(System.getenv(Backend4frontendRestApplication.ENVVAR_CART_SERVICE_URL));

        LOGGER.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_CART_SERVICE_URL, cartServiceUri));

        LOGGER.debug("createClient(): creating CartServiceClient");

        CartServiceClient cartServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(cartServiceUri.toURL())
                .build(CartServiceClient.class);

        LOGGER.debug("createClient(): successfully created CartServiceClient");

        return cartServiceClient;
    }
}
