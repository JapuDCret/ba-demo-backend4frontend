package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/order")
@RequestScoped
public class OrderForwardingResource {

    private static final Logger log = LoggerFactory.getLogger(OrderForwardingResource.class.getName());

    @Inject
    private OrderServiceClient orderServiceClient;

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
