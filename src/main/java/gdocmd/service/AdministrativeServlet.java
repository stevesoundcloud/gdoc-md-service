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
      "<form action=\"/save-service-properties\" method=\"POST\">\n" +
      "  OAuth Access Token: <input name=\"" + ServiceProperties.END_USER_OAUTH_ACCESS_TOKEN + "\" type=\"text\"></input><br/>\n" +
      "  OAuth Refresh Token: <input name=\"" + ServiceProperties.END_USER_OAUTH_REFRESH_TOKEN + "\" type=\"text\"></input><br/>\n" +
      "  <input type=\"submit\"></input>\n" +
      "</form>\n" +
      "</html>\n");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Map<String, String> simpleParameterMap = new HashMap<>();
    for(Object key: request.getParameterMap().keySet()) {
      simpleParameterMap.put(key.toString(), ((String[])request.getParameterMap().get(key))[0]);
    }

    serviceConfig.saveEndUserAccessTokenAndRefreshToken(
      simpleParameterMap.get(ServiceProperties.END_USER_OAUTH_ACCESS_TOKEN),
      simpleParameterMap.get(ServiceProperties.END_USER_OAUTH_REFRESH_TOKEN));
  }
}
