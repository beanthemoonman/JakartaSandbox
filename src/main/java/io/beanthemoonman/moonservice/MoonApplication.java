package io.beanthemoonman.moonservice;

import io.beanthemoonman.moonservice.api.MoonResource;
import io.beanthemoonman.moonservice.api.mappers.GenericExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.weld.util.collections.Sets;

import java.util.Set;

@ApplicationPath("/api")
public class MoonApplication extends Application {

  @Override
  public Set<Class<?>> getClasses() {
    return Sets.newHashSet(
        //apis
        MoonResource.class,

        //mappers
        GenericExceptionMapper.class
    );
  }
}