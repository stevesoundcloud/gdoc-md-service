package gdocmd.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.services.drive.DriveScopes;
import gdocmd.common.Functions;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static gdocmd.common.Functions.logInfo;
import static java.lang.String.format;
import static java.util.Arrays.asList;

// see https://developers.google.com/api-client-library/java/google-oauth-java-client/oauth2#servlet_authorization_code_flow
@Singleton
public class OauthAuthorizationCodeServlet extends AbstractAuthorizationCodeServlet {

  private final AuthorizationCodeFlowSource authorizationCodeFlowSource;

  @Inject
  public OauthAuthorizationCodeServlet(AuthorizationCodeFlowSource authorizationCodeFlowSource) {
    this.authorizationCodeFlowSource = authorizationCodeFlowSource;
  }

  private static final String GDOC_MD_SERVICE_OAUTH_USER_ID = "gdoc-md-service-oauth-user-id";
  public static final List<String> SCOPES = asList(DriveScopes.DRIVE);

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.getWriter().println(String.format("Hi, this is %s", getClass()));
    response.getWriter().println(getCredential().getAccessToken());
    response.getWriter().println(getCredential().getRefreshToken());
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return authorizationCodeFlowSource.getAuthorizationCodeFlow();
  }

  /**
   * The URI that Google with redirect back to after the user authorizes access (i.e. the end of the oauth flow)
   */
  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    String redirectUri = "http://localhost:8080/fetch-oauth-token";
    logInfo("OAUTH", format("GoogleAuthorizationCodeFlow complete, redirecting..."));
    return redirectUri;
  }

  @Override
  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
    return GDOC_MD_SERVICE_OAUTH_USER_ID;
  }
}