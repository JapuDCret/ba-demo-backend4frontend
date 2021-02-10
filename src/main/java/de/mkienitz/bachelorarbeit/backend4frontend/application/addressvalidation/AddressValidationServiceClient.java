package de.mkienitz.bachelorarbeit.backend4frontend.application.addressvalidation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/data/address-validation")
public interface AddressValidationServiceClient {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    Response validateAddress(String orderJson);
}
