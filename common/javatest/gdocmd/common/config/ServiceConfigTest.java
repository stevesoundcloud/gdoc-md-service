package gdocmd.common.config;

import gdocmd.common.config.storage.ServiceProperties;
import gdocmd.common.config.storage.SimulatedServicePropertiesStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ServiceConfigTest {

  private SimulatedServicePropertiesStorage storage;
  private ServiceConfig serviceConfig;

  @BeforeEach
  void before() {
    storage = new SimulatedServicePropertiesStorage();
    serviceConfig = new ServiceConfig(storage);
  }

  @Test
  public void saveClientIdAndSecret() {
    assertFalse(serviceConfig.loadGoogleClientSecretsFromGcsServicePropertiesFile().isPresent());

    serviceConfig.saveClientIdAndSecretAndGcsOutboxBucketName("client-id", "client-secret");

    ServiceProperties expected = new ServiceProperties();
    expected.appOauthClientId = "client-id";
    expected.appOauthClientSecret = "client-secret";
    assertEquals(expected, storage.serviceProperties.get());

    assertEquals(
      "client-id",
      serviceConfig.loadGoogleClientSecretsFromGcsServicePropertiesFile().get().getInstalled().getClientId());

    assertEquals(
      "client-secret",
      serviceConfig.loadGoogleClientSecretsFromGcsServicePropertiesFile().get().getInstalled().getClientSecret());
  }

  @Test
  public void saveEndUserAccessTokenAndRequestToken() {
    assertFalse(serviceConfig.loadGoogleCredentialFromServicePropertiesFile().isPresent());

    serviceConfig.saveEndUserAccessTokenAndRequestToken("access-token", "refresh-token");

    assertFalse(serviceConfig.loadGoogleCredentialFromServicePropertiesFile().isPresent());

    serviceConfig.saveClientIdAndSecretAndGcsOutboxBucketName("client-id", "client-secret");

    ServiceProperties expected = new ServiceProperties();
    expected.appOauthClientId = "client-id";
    expected.appOauthClientSecret = "client-secret";
    expected.endUserOauthAccessToken = "access-token";
    expected.endUserOauthRefreshToken = "refresh-token";
    assertEquals(expected, storage.serviceProperties.get());

    assertEquals(
      "access-token",
      serviceConfig.loadGoogleCredentialFromServicePropertiesFile().get().getAccessToken());

    assertEquals(
      "refresh-token",
      serviceConfig.loadGoogleCredentialFromServicePropertiesFile().get().getRefreshToken());
  }
}