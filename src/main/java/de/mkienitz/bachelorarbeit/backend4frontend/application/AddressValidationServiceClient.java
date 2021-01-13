package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RegisterRestClient
@Path("/data/address-validation")
public interface AddressValidationServiceClient {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    Response validateAddress(String orderJson);
}
