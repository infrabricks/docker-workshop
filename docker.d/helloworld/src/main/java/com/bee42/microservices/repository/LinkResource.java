package com.bee42.microservices.repository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.JsonObject;
import com.codahale.metrics.annotation.Timed;

@Path("/link")
public class LinkResource {

  static private LinkRepository repo ;
  static
  {
    repo = new LinkRepository() ;
    repo.init();
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Timed
  public String findAll() {
    return repo.findAny().toString();
  }
}
