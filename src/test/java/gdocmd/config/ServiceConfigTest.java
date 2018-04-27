package gdocmd.config;

import gdocmd.storage.ServiceProperties;
import gdocmd.storage.SimulatedServicePropertiesStorage;
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
  public void saveEndUserAccessTokenAndRequestToken() {
    assertFalse(serviceConfig.loadGoogleCredentialFromServicePropertiesFile().isPresent());

    serviceConfig.saveEndUserAccessTokenAndRefreshToken("access-token", "refresh-token");

    ServiceProperties expected = new ServiceProperties();
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