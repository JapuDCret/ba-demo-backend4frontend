package de.mkienitz.bachelorarbeit.backend4frontend.application;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        // use .putSingle instead of .add to replace the existing header
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        // maybe add Tracing Headers, e.g. https://github.com/opentracing/specification/blob/master/rfc/trace_identifiers.md#trace-context-http-headers
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, uber-trace-id");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().putSingle("Access-Control-Max-Age", "1209600");
    }
}
