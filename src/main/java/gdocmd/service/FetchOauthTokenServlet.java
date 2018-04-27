package gdocmd.service;

import com.google.api.client.auth.oauth2.TokenResponse;
import gdocmd.config.ServiceConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

@Singleton
public class FetchOauthTokenServlet extends HttpServlet {

  private final ServiceConfig serviceConfig;
  private final AuthorizationCodeFlowSource authorizationCodeFlowSource;

  @Inject
  public FetchOauthTokenServlet(ServiceConfig serviceConfig, AuthorizationCodeFlowSource authorizationCodeFlowSource) {
    this.serviceConfig = serviceConfig;
    this.authorizationCodeFlowSource = authorizationCodeFlowSource;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    String baseUrl = "Production".equals(System.getProperty("com.google.appengine.runtime.environment"))
      ? format("https://%s.appspot.com/", System.getProperty("com.google.appengine.application.id"))
      : "localhost:8080";

    TokenResponse tokenResponse =
      authorizationCodeFlowSource.getAuthorizationCodeFlow()
        .newTokenRequest(request.getParameter("code"))
        .setRedirectUri(format("%s/fetch-oauth-token", baseUrl))
        .execute();

    serviceConfig.saveEndUserAccessTokenAndRequestToken(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

    response.sendRedirect("/test-drive");
  }
}
