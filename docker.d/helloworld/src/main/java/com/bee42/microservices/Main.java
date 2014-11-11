package com.bee42.microservices;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import java.io.File;
import java.net.MalformedURLException;
//import org.apache.catalina.valves.AccessLogValve;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.jersey2.MetricsFeature;
import com.bee42.metrics.OnelineConsoleReporter;

import java.util.concurrent.TimeUnit;

import com.bee42.catalina.valves.AccessLogStdoutValve;

public class Main {
    static final MetricRegistry metrics = new MetricRegistry();
    static final String ACCESS_LOG_PATTERN = "%{begin:yyyy-MM-dd'T'HH:mm:ss:SSSZ}t " + AccessLogStdoutValve.class.getName() + " access INFO: %h %{begin:msec}t \"%r\" \"%{Referer}i\" \"%{User-Agent}i\" %s %b &quot;%I&quot; %D %S" ;
    static final String CONFIG_SERVICE_PATTERN = "SERVICE_PATTERN" ;
    static final String CONFIG_SERVICE_PORT = "SERVICE_PORT" ;
    static final String CONFIG_SERVICE_METRICS_SCHEDULING = "SERVICE_METRICS_SCHEDULING" ;

    public static void main(String[] args) throws Exception, LifecycleException
    {
        startReport();
        new Main().start();
    }

    public void start() throws ServletException, LifecycleException,
            MalformedURLException {

        // Define a folder to hold web application contents.
        String webappDirLocation = ".";
        Tomcat tomcat = new Tomcat();

        // Define port number for the web application
        String webPort = getConfigParameter(
          CONFIG_SERVICE_PORT, "8080");
        // Bind the port to Tomcat server
        tomcat.setPort(Integer.valueOf(webPort));

        // Define a web application context.
        Context context = tomcat.addWebapp("/hello", new File(
                webappDirLocation).getAbsolutePath());

       // Add servlet that will register Jersey REST resources
        Tomcat.addServlet(context, "jersey-container-servlet", resourceConfig());
        context.addServletMapping("/rest/*", "jersey-container-servlet");

/*
        AccessLogValve valve = new AccessLogValve();
        valve.setDirectory(webappDirLocation);
        valve.setPrefix("access");
        valve.setSuffix(".log");
        valve.setRotatable(false);
        valve.setPattern("%h \"%{begin:yyyy-MM-dd'T'HH:mm:ss:SSSZ}t\" %{begin:msec}t \"%r\" \"%{Referer}i\" \"%{User-Agent}i\" %s %b &quot;%I&quot; %D %S");
        context.getPipeline().addValve(valve);
        */

        AccessLogStdoutValve valve = new AccessLogStdoutValve();
        String pattern = getConfigParameter(
          CONFIG_SERVICE_PATTERN, ACCESS_LOG_PATTERN);
        valve.setPattern(pattern);
        context.getPipeline().addValve(valve);

        tomcat.start();
        tomcat.getServer().await();
    }

    static void startReport() {
  /*
      ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
          .convertRatesTo(TimeUnit.SECONDS)
          .convertDurationsTo(TimeUnit.MILLISECONDS)
          .build();
      String time = getConfigParameter(
        CONFIG_SERVICE_METRICS_SCHEDULING,"30");
      reporter.start(Integer.parseInt(time), TimeUnit.SECONDS);
    */
      OnelineConsoleReporter reporter = OnelineConsoleReporter.forRegistry(metrics)
          .convertRatesTo(TimeUnit.SECONDS)
          .convertDurationsTo(TimeUnit.MILLISECONDS)
          .build();
      String time = getConfigParameter(
        CONFIG_SERVICE_METRICS_SCHEDULING,"10");
      reporter.start(Integer.parseInt(time), TimeUnit.SECONDS);
    }

    private ServletContainer resourceConfig() {
        return new ServletContainer(configure());
    }

    private ResourceConfig configure() {

      ResourceConfig config = new ResourceConfig(
        new ResourceLoader().getClasses());
      config = config.register(new MetricsFeature(metrics));
      return config;
    }

    /**
     * replace '.' with '_' + uppercase for getenv
     * replace '_' with '.' + lowercase for getProperty
     */
    public static String getConfigParameter(String key, String defaultValue) {
      if ( key != null && !"".equals(key)) {
        String envKey = key.replace(".","_").toUpperCase();
        String value = System.getenv(envKey);
        if(value == null || "".equals(value)) {
           String propKey = key.replace("_",".").toLowerCase();
           value = System.getProperty(propKey);
           if(value == null || "".equals(value)) {
             value = defaultValue;
           }
        }
        return value;
      }
      return defaultValue ;
    }

}
