package gdocmd.service;

import gdocmd.config.ServiceConfig;
import gdocmd.storage.ServiceProperties;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
      "  OAuth Client Id: <input name=\"" + ServiceProperties.APP_OAUTH_CLIENT_ID + "\" type=\"text\"></input><br/>\n" +
      "  OAuth Client Secret: <input name=\"" + ServiceProperties.APP_OAUTH_CLIENT_SECRET + "\" type=\"text\"></input><br/>\n" +
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

    serviceConfig.saveClientIdAndSecret(
      simpleParameterMap.get(ServiceProperties.APP_OAUTH_CLIENT_ID),
      simpleParameterMap.get(ServiceProperties.APP_OAUTH_CLIENT_SECRET));
  }
}
