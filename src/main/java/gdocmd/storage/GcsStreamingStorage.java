package gdocmd.storage;

import java.io.OutputStream;

import static gdocmd.storage.GcsFunctions.saveToGcs;
import static gdocmd.storage.GcsFunctions.streamToGcs;

public class GcsStreamingStorage implements StreamingStorage {
  @Override
  public void writeFileToGcs(String path, String mimetype, byte[] fileContent) {
    saveToGcs(path, mimetype, fileContent);
  }

  @Override
  public OutputStream createUploadOutputStream(String fileName, String mimeType) {
    return streamToGcs(fileName, mimeType);
  }
}
