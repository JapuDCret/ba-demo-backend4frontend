package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import org.eclipse.microprofile.opentracing.Traced;

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

    @Inject
    private OrderForwardingApplicationService service;

    @POST
    @Traced(operationName = "OrderForwardingResource.forwardOrder")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response forwardOrder(
            String orderJson
    ) {
        Response orderResponse = service.order(orderJson);

        return orderResponse;
    }
}
