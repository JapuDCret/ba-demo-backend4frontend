package de.mkienitz.bachelorarbeit.backend4frontend.application.addressvalidation;

import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
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
import java.net.MalformedURLException;
import java.net.URL;

@Path("/address-validation")
@RequestScoped
public class AddressValidationForwardingResource {

    private static final Logger log = LoggerFactory.getLogger(AddressValidationForwardingResource.class.getName());

    @Inject
    private AddressValidationServiceClient addressValidationServiceClient;

    @POST
    @Traced(operationName = "AddressValidationForwardingResource.addressValidation")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addressValidation(
            String addressValidationJson
    ) {
        try {
            Response addressValidationResponse = addressValidationServiceClient.validateAddress(addressValidationJson);

            log.info("addressValidation(): status = " + addressValidationResponse.getStatus());

            return addressValidationResponse;
        } catch(Exception e) {
            log.warn("addressValidation(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
