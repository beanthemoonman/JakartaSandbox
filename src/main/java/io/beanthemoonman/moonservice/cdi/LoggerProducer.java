package io.beanthemoonman.moonservice.cdi;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

import java.util.logging.Logger;

@Dependent
public class LoggerProducer {

  @Produces
  public Logger getLogger(InjectionPoint p) {
    return Logger.getLogger(p.getMember().getDeclaringClass().getName());
  }

}