package gdocmd.gdrive;

import java.util.List;

public interface GDrive {
  List<GDriveFileReference> listFiles();
  String exportToHtml(GDriveFileReference fileReference);
}
