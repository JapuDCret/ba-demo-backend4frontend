package de.mkienitz.bachelorarbeit.backend4frontend.application;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/trace")
@Singleton
public class JaegerForwardingResource {

    private static Logger log = LoggerFactory.getLogger(JaegerForwardingResource.class.getName());

    private final JaegerTracer tracer;

    public JaegerForwardingResource() {
        JaegerTracer tracer = Configuration.fromEnv("frontend").getTracer();

        this.tracer = tracer;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response trace(String trace) {
        try {
            log.info("trace(): trace = " + trace);

            // Response sendTraceResponse = jaegerClient.sendTrace(trace);

            return Response.ok().build();
        } catch(Exception e) {
            log.warn("trace(): an unknown error occurred, e = ", e);

            throw e;
        }
    }
}
