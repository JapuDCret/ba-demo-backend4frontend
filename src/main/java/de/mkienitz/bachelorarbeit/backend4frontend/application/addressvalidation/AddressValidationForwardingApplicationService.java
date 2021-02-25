package de.mkienitz.bachelorarbeit.backend4frontend.application.addressvalidation;

import org.eclipse.microprofile.opentracing.Traced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class AddressValidationForwardingApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressValidationForwardingApplicationService.class.getName());

    @Inject
    private AddressValidationServiceClient addressValidationServiceClient;

    @Traced(operationName = "AddressValidationForwardingApplicationService.addressValidation")
    public Response addressValidation(
            String addressValidationJson
    ) {
        try {
            Response addressValidationResponse = addressValidationServiceClient.validateAddress(addressValidationJson);

            LOGGER.info("addressValidation(): status = " + addressValidationResponse.getStatus());

            return addressValidationResponse;
        } catch(Exception e) {
            LOGGER.warn("addressValidation(): an unknown error occurred, e = ", e);

            final String errMsg = "Anfrage zum Adressvalidierungsdienst war nicht erfolgreich, Fehler = " + e.getMessage();

            return Response.status(Response.Status.BAD_GATEWAY).entity(errMsg).build();
        }
    }
}
