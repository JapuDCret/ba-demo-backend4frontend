package de.mkienitz.bachelorarbeit.backend4frontend.application;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@RegisterRestClient
@Path("/api/traces")
public interface JaegerClient {

    @GET
    @Consumes("application/vnd.apache.thrift.binary")
    Response sendTrace(InputStream trace);
}
