package gdocmd.common.config.storage;

import com.google.common.base.Throwables;
import gdocmd.common.Functions;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import static java.lang.String.format;

public class ServiceProperties {
  public static final String APP_OAUTH_CLIENT_ID = "oauth-client-id";
  public static final String APP_OAUTH_CLIENT_SECRET = "oauth-client-secret";
  public static final String END_USER_OAUTH_ACCESS_TOKEN = "end-user-oauth-access-token";
  public static final String END_USER_OAUTH_REQUEST_TOKEN = "end-user-oauth-request-token";

  public static ServiceProperties load(String propertiesContent) {
    try {
      Properties existingProperties = new Properties();
      existingProperties.load(new StringReader(propertiesContent));
      ServiceProperties sp = new ServiceProperties();
      sp.appOauthClientId = existingProperties.getProperty(APP_OAUTH_CLIENT_ID);
      sp.appOauthClientSecret = existingProperties.getProperty(APP_OAUTH_CLIENT_SECRET);
      sp.endUserOauthAccessToken = existingProperties.getProperty(END_USER_OAUTH_ACCESS_TOKEN);
      sp.endUserOauthRefreshToken = existingProperties.getProperty(END_USER_OAUTH_REQUEST_TOKEN);
      return sp;
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  public String appOauthClientId;
  public String appOauthClientSecret;
  public String endUserOauthAccessToken;
  public String endUserOauthRefreshToken;

  public Properties asJavaUtilProperties() {
    Properties p = new Properties();
    if (appOauthClientId != null) p.setProperty(APP_OAUTH_CLIENT_ID, appOauthClientId);
    if (appOauthClientSecret != null) p.setProperty(APP_OAUTH_CLIENT_SECRET, appOauthClientSecret);
    if (endUserOauthAccessToken != null) p.setProperty(END_USER_OAUTH_ACCESS_TOKEN, endUserOauthAccessToken);
    if (endUserOauthRefreshToken != null) p.setProperty(END_USER_OAUTH_REQUEST_TOKEN, endUserOauthRefreshToken);
    return p;
  }

  public String toString() {
    return format("ServiceProperties: clientid=%s clientsecret=%s accesstoken=%s requesttoken=%s",
      appOauthClientId,
      Functions.mask(appOauthClientSecret),
      Functions.mask(endUserOauthAccessToken),
      Functions.mask(endUserOauthRefreshToken));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ServiceProperties that = (ServiceProperties) o;

    if (appOauthClientId != null ? !appOauthClientId.equals(that.appOauthClientId) : that.appOauthClientId != null)
      return false;
    if (appOauthClientSecret != null ? !appOauthClientSecret.equals(that.appOauthClientSecret) : that.appOauthClientSecret != null)
      return false;
    if (endUserOauthAccessToken != null ? !endUserOauthAccessToken.equals(that.endUserOauthAccessToken) : that.endUserOauthAccessToken != null)
      return false;
    return endUserOauthRefreshToken != null ? endUserOauthRefreshToken.equals(that.endUserOauthRefreshToken) : that.endUserOauthRefreshToken == null;
  }

  @Override
  public int hashCode() {
    int result = appOauthClientId != null ? appOauthClientId.hashCode() : 0;
    result = 31 * result + (appOauthClientSecret != null ? appOauthClientSecret.hashCode() : 0);
    result = 31 * result + (endUserOauthAccessToken != null ? endUserOauthAccessToken.hashCode() : 0);
    result = 31 * result + (endUserOauthRefreshToken != null ? endUserOauthRefreshToken.hashCode() : 0);
    return result;
  }
}
