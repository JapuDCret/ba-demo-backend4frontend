package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URI;

// can't use ApplicationScoped directly on class, since it's a CDI-managed Producer
public class OrderServiceClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceClientFactory.class.getName());

    @javax.enterprise.inject.Produces
    @ApplicationScoped
    public OrderServiceClient createClient() throws MalformedURLException {
        URI orderServiceUri = URI.create(System.getenv(Backend4frontendRestApplication.ENVVAR_ORDER_SERVICE_URL));

        LOGGER.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_ORDER_SERVICE_URL, orderServiceUri));

        LOGGER.debug("createClient(): creating OrderServiceClient");

        OrderServiceClient orderServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(orderServiceUri.toURL())
                .build(OrderServiceClient.class);

        LOGGER.debug("createClient(): successfully created OrderServiceClient");

        return orderServiceClient;
    }
}
