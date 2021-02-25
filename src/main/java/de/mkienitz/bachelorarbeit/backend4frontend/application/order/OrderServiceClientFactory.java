package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import de.mkienitz.bachelorarbeit.backend4frontend.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.net.MalformedURLException;
import java.net.URI;

@ApplicationScoped
public class OrderServiceClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceClientFactory.class.getName());

    @Produces
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
