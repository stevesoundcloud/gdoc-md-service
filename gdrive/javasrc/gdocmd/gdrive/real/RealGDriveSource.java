package gdocmd.gdrive.real;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import gdocmd.common.config.ServiceConfig;
import gdocmd.common.config.storage.GcsServicePropertiesStorage;
import gdocmd.gdrive.GDrive;
import gdocmd.gdrive.GDriveSource;

import javax.inject.Inject;
import java.util.Optional;

public class RealGDriveSource implements GDriveSource {
  private final ServiceConfig serviceConfig;

  @Inject
  public RealGDriveSource(ServiceConfig serviceConfig) {
    this.serviceConfig = serviceConfig;
  }

  @Override
  public Optional<GDrive> maybeLoadGDrive() {
    Optional<GoogleCredential> maybeGoogleCredential =
      new ServiceConfig(new GcsServicePropertiesStorage())
        .loadGoogleCredentialFromServicePropertiesFile();

    return maybeGoogleCredential.isPresent()
      ? Optional.of(new RealGDrive(maybeGoogleCredential.get()))
      : Optional.empty();
  }
}
