package de.mkienitz.bachelorarbeit.backend4frontend.application.addressvalidation;

import de.mkienitz.bachelorarbeit.backend4frontend.application.Backend4frontendRestApplication;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.MalformedURLException;
import java.net.URI;

// can't use ApplicationScoped directly on class, since it's a CDI-managed Producer
public class AddressValidationServiceClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressValidationServiceClientFactory.class.getName());

    @javax.enterprise.inject.Produces
    @ApplicationScoped
    public AddressValidationServiceClient createClient() throws MalformedURLException {
        URI addressValidationServiceUri = URI.create(System.getenv(Backend4frontendRestApplication.ENVVAR_ADDRESSVALIDATION_SERVICE_URL));

        LOGGER.info(String.format("createClient(): env.%s = %s", Backend4frontendRestApplication.ENVVAR_ADDRESSVALIDATION_SERVICE_URL, addressValidationServiceUri));

        LOGGER.debug("createClient(): creating AddressValidationServiceClient");

        AddressValidationServiceClient addressValidationServiceClient = RestClientBuilder
                .newBuilder()
                .baseUrl(addressValidationServiceUri.toURL())
                .build(AddressValidationServiceClient.class);

        LOGGER.debug("createClient(): successfully created AddressValidationServiceClient");

        return addressValidationServiceClient;
    }
}
