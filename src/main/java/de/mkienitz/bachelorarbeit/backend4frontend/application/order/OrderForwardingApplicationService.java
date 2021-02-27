package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class OrderForwardingApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderForwardingApplicationService.class.getName());

    @Inject
    private OrderServiceClient client;

    @Traced(operationName = "OrderForwardingApplicationService.order")
    public Response order(
            String orderJson
    ) {
        try {
            Response orderResponse = client.order(orderJson);

            LOGGER.info("order(): status = " + orderResponse.getStatus());

            return orderResponse;
        } catch(Exception e) {
            LOGGER.warn("order(): an unknown error occurred, e = ", e);

            final String errMsg = "Anfrage zum Bestelldienst war nicht erfolgreich, Fehler = " + e.getMessage();

            return Response.status(Response.Status.BAD_GATEWAY).entity(errMsg).build();
        }
    }
}
