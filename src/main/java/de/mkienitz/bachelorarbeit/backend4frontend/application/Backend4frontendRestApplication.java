package de.mkienitz.bachelorarbeit.backend4frontend.application;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ApplicationPath("/data")
public class Backend4frontendRestApplication extends Application {

    public Backend4frontendRestApplication() {

    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();

        s.add(CORSFilter.class);
        s.add(AddressValidationForwardingResource.class);
        s.add(CartForwardingResource.class);
        s.add(LocalizationForwardingResource.class);
        s.add(OrderForwardingResource.class);

        return s;
    }
}
