package io.beanthemoonman.moonservice;

import io.beanthemoonman.moonservice.api.MoonV1Resource;
import io.beanthemoonman.moonservice.api.MoonV2Resource;
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
        MoonV1Resource.class,
        MoonV2Resource.class,

        //mappers
        GenericExceptionMapper.class
    );
  }
}