package io.beanthemoonman.moonservice.integrations;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public abstract class AbstractIntegration {

  protected static String makeSimpleGetRequest(String URL) throws IOException {
    var client = new OkHttpClient();
    var req = new Request.Builder()
        .url(URL)
        .build();
    var resp = client.newCall(req).execute();
    var ret = resp.body() != null ? resp.body().string() : "";
    resp.body().close();
    return ret;
  }
}
