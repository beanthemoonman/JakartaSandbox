package io.beanthemoonman.moonservice.integrations;

import java.io.IOException;

public class IfConfig extends AbstractIntegration {

  private static final String API_URL = "https://ifconfig.io/";

  public static String getServerIp() throws IOException {
    return makeSimpleGetRequest(API_URL + "ip");
  }
}
