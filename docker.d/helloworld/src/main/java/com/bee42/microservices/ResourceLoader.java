package com.bee42.microservices;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.bee42.microservices.repository.LinkResource;

public class ResourceLoader extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();

        // register root resource
        classes.add(HelloWorldRestResource.class);
        classes.add(StatusResource.class);
        classes.add(LinkResource.class);
        return classes;
    }
}
