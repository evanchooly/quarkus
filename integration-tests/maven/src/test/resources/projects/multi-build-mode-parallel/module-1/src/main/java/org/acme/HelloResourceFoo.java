package org.acme;

import io.quarkus.arc.profile.IfBuildProfile;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@IfBuildProfile("foo-1")
@Path("/hello")
public class HelloResourceFoo {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello foo 1";
    }
}
