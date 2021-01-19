package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 */
@Path("/order")
@Singleton
public class OrderForwardingResource {

    public static final String SYS_ENV_VAR_ORDER_SERVICE_URL = "BA_ORDER_SERVICE_URL";

    private static Logger log = LoggerFactory.getLogger(OrderForwardingResource.class.getName());

    private OrderServiceClient orderServiceClient;

    public OrderForwardingResource() throws MalformedURLException {
        String orderServiceUrl = System.getenv(SYS_ENV_VAR_ORDER_SERVICE_URL);

        log.info(String.format("OrderForwardingResource(): env.%s = %s", SYS_ENV_VAR_ORDER_SERVICE_URL, orderServiceUrl));

        log.debug("OrderForwardingResource(): creating OrderServiceClient");

        URL orderServiceUrl2 = new URL(orderServiceUrl);

        OrderServiceClient orderServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(orderServiceUrl2)
                .build(OrderServiceClient.class);

        log.debug("OrderForwardingResource(): successfully created OrderServiceClient");

        this.orderServiceClient = orderServiceClient;
    }

    @POST
    @Traced(operationName = "OrderForwardingResource.order")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response order(
            String orderJson
    ) {
        try {
            Response orderResponse = orderServiceClient.order(orderJson);

            log.info("order(): status = " + orderResponse.getStatus());

            return orderResponse;
        } catch(Exception e) {
            log.warn("order(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
