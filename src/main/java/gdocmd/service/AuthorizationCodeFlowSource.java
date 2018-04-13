package gdocmd.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import gdocmd.common.Functions;
import gdocmd.config.ServiceConfig;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;

import static gdocmd.common.Functions.logInfo;

public class AuthorizationCodeFlowSource {
  private final ServiceConfig serviceConfig;

  @Inject
  public AuthorizationCodeFlowSource(ServiceConfig serviceConfig) {
    this.serviceConfig = serviceConfig;
  }

  public AuthorizationCodeFlow getAuthorizationCodeFlow() throws IOException {
    GoogleClientSecrets clientSecrets = serviceConfig.loadGoogleClientSecretsFromGcsServicePropertiesFile().get();

    logInfo("OAUTH", "Initiating GoogleAuthorizationCodeFlow...");

    try {
      return new GoogleAuthorizationCodeFlow.Builder(
        GoogleNetHttpTransport.newTrustedTransport(),
        JacksonFactory.getDefaultInstance(),
        clientSecrets,
        OauthAuthorizationCodeServlet.SCOPES)
        .setDataStoreFactory(new DataStoreFactory() {
          @Override
          public <V extends Serializable> DataStore<V> getDataStore(String s) throws IOException {
            return new SimulatedDataStore<>();
          }
        })


        // From: https://developers.google.com/api-client-library/java/google-api-java-client/oauth2#gae-id
        // "An access token typically has an expiration date of 1 hour, after which you will get an error if you try
        //  to use it. GoogleCredential takes care of automatically "refreshing" the token, which simply means getting
        //  a new access token. This is done by means of a long-lived refresh token, which is typically received along
        //  with the access token if you use the access_type=offline parameter during the authorization code flow"
        .setAccessType("offline")
        .build();
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
}
