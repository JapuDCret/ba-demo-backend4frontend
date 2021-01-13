package de.mkienitz.bachelorarbeit.backend4frontend.application;

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
@Path("/address-validation")
@Singleton
public class AddressValidationForwardingResource {

    public static final String SYS_ENV_VAR_ADDRESSVALIDATION_SERVICE_URL = "BA_ADDRESSVALIDATION_SERVICE_URL";

    private static Logger log = LoggerFactory.getLogger(AddressValidationForwardingResource.class.getName());

    private AddressValidationServiceClient addressValidationServiceClient;

    public AddressValidationForwardingResource() throws MalformedURLException {
        String addressValidationServiceUrl = System.getenv(SYS_ENV_VAR_ADDRESSVALIDATION_SERVICE_URL);

        log.info(String.format("AddressValidationForwardingResource(): env.%s = %s", SYS_ENV_VAR_ADDRESSVALIDATION_SERVICE_URL, addressValidationServiceUrl));

        log.debug("AddressValidationForwardingResource(): creating AddressValidationServiceClient");

        URL addressValidationServiceUrl2 = new URL(addressValidationServiceUrl);

        AddressValidationServiceClient addressValidationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(addressValidationServiceUrl2)
                .build(AddressValidationServiceClient.class);

        log.debug("AddressValidationForwardingResource(): successfully created AddressValidationServiceClient");

        this.addressValidationServiceClient = addressValidationServiceClient;
    }

    @POST
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
