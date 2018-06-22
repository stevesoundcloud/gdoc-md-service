package gdocmd.storage;

import com.google.api.client.util.IOUtils;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.zip.GZIPOutputStream;

import static com.google.appengine.tools.cloudstorage.GcsServiceFactory.createGcsService;
import static gdocmd.common.Functions.copyStream;
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
    try (OutputStream outputStream = streamToGcs(filename, mimetype)) {
      copyStream(new ByteArrayInputStream(fileContents), outputStream);
    } catch (IOException ex) {
      throw Throwables.propagate(ex);
    }
  }

  public static OutputStream streamToGcs(String filename, String mimetype) {
    GcsFilename gcsFile = gcsFile(filename);

    try {
      return newOutputStream(
        createGcsService().createOrReplace(
          gcsFile,
          new GcsFileOptions.Builder()
            .mimeType(mimetype)
            .build()));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  public static class TarEntry {
    public final String tarEntryName;
    public final byte[] entryContent;

    public TarEntry(String tarEntryName, byte[] entryContent) {
      this.tarEntryName = tarEntryName;
      this.entryContent = entryContent;
    }
  }

  public static void streamUploadTarGz(
    String tarGzFileName,
    StreamingStorage storage,
    Iterable<TarEntry> tarEntries) {
    try (
      OutputStream uploadOutputStream = storage.createUploadOutputStream(tarGzFileName, "application/tar+gzip");
      BufferedOutputStream bufStream = new BufferedOutputStream(uploadOutputStream);
      GZIPOutputStream gzipStream = new GZIPOutputStream(bufStream);
      TarArchiveOutputStream tarGzStream = new TarArchiveOutputStream(gzipStream)) {

      for(TarEntry entry: tarEntries) {
        TarArchiveEntry archiveEntry = new TarArchiveEntry(entry.tarEntryName);
        archiveEntry.setSize(entry.entryContent.length);
        tarGzStream.putArchiveEntry(archiveEntry);
        tarGzStream.write(entry.entryContent);
        tarGzStream.closeArchiveEntry();
      }
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
