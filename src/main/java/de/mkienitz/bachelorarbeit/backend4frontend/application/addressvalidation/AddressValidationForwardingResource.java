package de.mkienitz.bachelorarbeit.backend4frontend.application.addressvalidation;

import org.eclipse.microprofile.opentracing.Traced;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/address-validation")
@RequestScoped
public class AddressValidationForwardingResource {

    @Inject
    private AddressValidationForwardingApplicationService service;

    @POST
    @Traced(operationName = "AddressValidationForwardingResource.addressValidation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addressValidation(
            String addressValidationJson
    ) {
        Response addressValidationResponse = service.addressValidation(addressValidationJson);

        return addressValidationResponse;
    }
}
