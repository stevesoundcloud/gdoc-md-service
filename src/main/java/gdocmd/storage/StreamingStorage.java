package gdocmd.storage;

import java.io.InputStream;

public interface StreamingStorage {
  void writeFileToGcs(String path, String mimetype, InputStream stream);
}
