package gdocmd.service;

import com.google.api.client.auth.oauth2.TokenResponse;
import gdocmd.common.config.ServiceConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    TokenResponse tokenResponse =
      authorizationCodeFlowSource.getAuthorizationCodeFlow()
        .newTokenRequest(request.getParameter("code"))
        .setRedirectUri("http://localhost:8080/fetch-oauth-token")
        .execute();

    serviceConfig.saveEndUserAccessTokenAndRequestToken(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());

    response.sendRedirect("/test-drive");
  }
}
