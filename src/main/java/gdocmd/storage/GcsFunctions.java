package gdocmd.storage;

import com.google.api.client.util.IOUtils;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.common.base.Throwables;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.Optional;

import static com.google.appengine.tools.cloudstorage.GcsServiceFactory.createGcsService;
import static java.nio.channels.Channels.newOutputStream;

public class GcsFunctions {
  private static final String STORAGE_BUCKET_NAME = AppIdentityServiceFactory.getAppIdentityService().getDefaultGcsBucketName();

  static Optional<String> readFromGcs(String filename) {
    GcsFilename gcsFile = gcsFile(filename);

    try {
      ByteArrayOutputStream ostream = new ByteArrayOutputStream();
      IOUtils.copy(
        Channels.newInputStream(createGcsService().openReadChannel(gcsFile, 0)),
        ostream);
      return Optional.of(new String(ostream.toByteArray()));
    } catch (FileNotFoundException fex) {
      return Optional.empty();
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static GcsFilename gcsFile(String filename) {
    return new GcsFilename(STORAGE_BUCKET_NAME, filename);
  }

  public static void saveToGcs(String filename, String mimetype, byte[] fileContents) {
    saveToGcs(filename, mimetype, new ByteArrayInputStream(fileContents));
  }

  public static void saveToGcs(String filename, String mimetype, InputStream fileContents) {
    GcsFilename gcsFile = gcsFile(filename);

    try {
      IOUtils.copy(
        fileContents,
        newOutputStream(
          createGcsService().createOrReplace(
            gcsFile,
            new GcsFileOptions.Builder()
              .mimeType(mimetype)
              .build())));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
