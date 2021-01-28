package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Set;

@Logged
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {

    private static Logger log = LoggerFactory.getLogger(ResponseLoggingFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {
        // Use the ContainerRequestContext to extract information from the HTTP request
        MultivaluedMap<String, String> headers = requestContext.getHeaders();

        StringBuilder logMessage = new StringBuilder();
        
        Set<String> headerNames = headers.keySet();
        for(String headerName : headerNames) {
            logMessage.append(headerName + "=\"" + headers.get(headerName) + "\"");
        }
        // Use the ContainerResponseContext to extract information from the HTTP response
    }
}