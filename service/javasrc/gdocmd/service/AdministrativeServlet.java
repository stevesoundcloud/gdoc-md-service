package gdocmd.service;

import gdocmd.common.config.ServiceConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static gdocmd.common.config.storage.ServiceProperties.APP_OAUTH_CLIENT_ID;
import static gdocmd.common.config.storage.ServiceProperties.APP_OAUTH_CLIENT_SECRET;
import static gdocmd.common.config.storage.ServiceProperties.GCS_OUTBOX_BUCKET_NAME;

@Singleton
public class AdministrativeServlet extends HttpServlet {

  private final ServiceConfig serviceConfig;

  @Inject
  public AdministrativeServlet(ServiceConfig serviceConfig) {
    this.serviceConfig = serviceConfig;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    response.getWriter().println("" +
      "<html>\n" +
      "Allow this service to have read access to your GDrive by <a href=\"/do-oauth-flow\">clicking here.</a>\n" +
      "<p>\n" +
      "<form action=\"/save-service-properties\" method=\"POST\">\n" +
      "  OAuth Client Id: <input name=\"" + APP_OAUTH_CLIENT_ID + "\" type=\"text\"></input><br/>\n" +
      "  OAuth Client Secret: <input name=\"" + APP_OAUTH_CLIENT_SECRET + "\" type=\"text\"></input><br/>\n" +
      "  Outbox GCS Bucket: <input name=\"" + GCS_OUTBOX_BUCKET_NAME + "\" type=\"text\"></input><br/>\n" +
      "  <input type=\"submit\"></input>\n" +
      "</form>\n" +
      "</p>\n" +
      "</html>\n");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Map<String, String> simpleParameterMap = new HashMap<>();
    for(Object key: request.getParameterMap().keySet()) {
      simpleParameterMap.put(key.toString(), ((String[])request.getParameterMap().get(key))[0]);
    }

    serviceConfig.saveClientIdAndSecretAndGcsOutboxBucketName(
      simpleParameterMap.get(APP_OAUTH_CLIENT_ID),
      simpleParameterMap.get(APP_OAUTH_CLIENT_SECRET),
      simpleParameterMap.get(GCS_OUTBOX_BUCKET_NAME));
  }
}
