package de.mkienitz.bachelorarbeit.backend4frontend.application.order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/data/order")
public interface OrderServiceClient {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response order(String orderJson);
}
