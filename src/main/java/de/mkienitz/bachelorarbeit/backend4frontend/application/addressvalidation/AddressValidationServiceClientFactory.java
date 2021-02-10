package de.mkienitz.bachelorarbeit.backend4frontend.application.addressvalidation;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import java.net.MalformedURLException;
import java.net.URL;

public class AddressValidationServiceClientFactory {

    private static final Logger log = LoggerFactory.getLogger(AddressValidationServiceClientFactory.class.getName());

    @Produces
    public AddressValidationServiceClient createClient() throws MalformedURLException {
        String addressValidationServiceUrl = System.getenv(Backend4frontendRestApplication.ENVVAR_ADDRESSVALIDATION_SERVICE_URL);

        log.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_ADDRESSVALIDATION_SERVICE_URL, addressValidationServiceUrl));

        log.debug("createClient(): creating AddressValidationServiceClient");

        URL addressValidationServiceUrl2 = new URL(addressValidationServiceUrl);

        AddressValidationServiceClient addressValidationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(addressValidationServiceUrl2)
                .build(AddressValidationServiceClient.class);

        log.debug("createClient(): successfully created AddressValidationServiceClient");

        return addressValidationServiceClient;
    }
}
