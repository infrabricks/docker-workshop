package com.bee42.microservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.codahale.metrics.annotation.Timed;

@Path("/helloworld")
public class HelloWorldRestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Timed
    public String sayHello() {
        return "Hello World from Tomcat Embedded with Jersey!\n";
    }

}
