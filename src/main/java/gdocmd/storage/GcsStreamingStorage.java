package gdocmd.storage;

import java.io.InputStream;

import static gdocmd.storage.GcsFunctions.saveToGcs;

public class GcsStreamingStorage implements StreamingStorage {
  @Override
  public void writeFileToGcs(String path, String mimetype, InputStream stream) {
    saveToGcs(path, mimetype, stream);
  }
}
