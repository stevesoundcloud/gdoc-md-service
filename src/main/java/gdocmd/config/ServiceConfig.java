package gdocmd.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.base.Throwables;
import gdocmd.storage.ServiceProperties;
import gdocmd.storage.ServicePropertiesStorage;

import javax.inject.Inject;
import java.util.Optional;

public class ServiceConfig {
  private final ServicePropertiesStorage servicePropertiesStorage;

  @Inject
  public ServiceConfig(ServicePropertiesStorage servicePropertiesStorage) {
    this.servicePropertiesStorage = servicePropertiesStorage;
  }

  public void saveEndUserAccessTokenAndRefreshToken(String accessToken, String refreshToken) {
    ServiceProperties sp = new ServiceProperties();
    Optional<ServiceProperties> maybeServiceProperties = servicePropertiesStorage.load();
    if (maybeServiceProperties.isPresent()) {
      sp = maybeServiceProperties.get();
    }
    sp.endUserOauthAccessToken = accessToken;
    sp.endUserOauthRefreshToken = refreshToken;
    servicePropertiesStorage.save(sp);
  }

  public Optional<GoogleCredential> loadGoogleCredentialFromServicePropertiesFile() {
    Optional<ServiceProperties> maybeServiceProperties = servicePropertiesStorage.load();
    if (!maybeServiceProperties.isPresent()) {
      return Optional.empty();
    }
    ServiceProperties serviceProperties = maybeServiceProperties.get();
    if (serviceProperties.endUserOauthAccessToken == null || serviceProperties.endUserOauthAccessToken == null) {
      return Optional.empty();
    }

    try {
      // see https://github.com/googlegenomics/utils-java/issues/8#issuecomment-59673175
      return Optional.of(
        new GoogleCredential.Builder()
          .setTransport(GoogleNetHttpTransport.newTrustedTransport())
          .setJsonFactory(JacksonFactory.getDefaultInstance())
          .setClientSecrets("doesnt", "matter")
          .build()
          .setAccessToken(serviceProperties.endUserOauthAccessToken)
          .setRefreshToken(serviceProperties.endUserOauthRefreshToken));
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }
}
