package gdocmd.storage;

import java.io.OutputStream;

public interface StreamingStorage {
  void writeFileToGcs(String path, String mimetype, byte[] fileContent);
  OutputStream createUploadOutputStream(String fileName, String mimeType);
}
