package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.net.MalformedURLException;
import java.net.URL;

public class OrderServiceClientFactory {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceClientFactory.class.getName());

    @Produces
    public OrderServiceClient createClient() throws MalformedURLException {
        String orderServiceUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_ORDER_SERVICE_URL);

        log.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_ORDER_SERVICE_URL, orderServiceUrl));

        log.debug("createClient(): creating OrderServiceClient");

        URL orderServiceUrl2 = new URL(orderServiceUrl);

        OrderServiceClient orderServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(orderServiceUrl2)
                .build(OrderServiceClient.class);

        log.debug("createClient(): successfully created OrderServiceClient");

        return orderServiceClient;
    }
}
