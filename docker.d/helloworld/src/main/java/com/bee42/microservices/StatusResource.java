package com.bee42.microservices;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.codahale.metrics.annotation.Timed;

@Path("/status")
public class StatusResource {

  @GET
  @Produces(MediaType.TEXT_HTML)
  @Timed
  public String status() {
    try {
      java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      String status = "<html>\n"
      + "<body>\n"
      + "<h1>Docker Tomcat Status page</h1>\n"
      + "<ul>\n"
      + "<li>Hostname : " +  java.net.InetAddress.getLocalHost().getHostName() + "</li>\n"
      + "<li>Now : " + dateFormat.format(new java.util.Date()) + "</li>\n"
      + "</ul>\n"
      + "</body>\n"
      + "</html>\n"
      ;
      return status;
    } catch (Exception e) {
      // ToDo: send status code 503
      return "error";
    }
  }
}
