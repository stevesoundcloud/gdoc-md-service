package gdocmd.gdrive;

import java.util.Optional;

public interface GDriveSource {
  Optional<GDrive> maybeLoadGDrive();
}
