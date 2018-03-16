package gdocmd.real;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import com.google.common.base.Throwables;
import com.google.common.io.ByteStreams;
import gdocmd.gdrive.GDrive;
import gdocmd.gdrive.GDriveFileReference;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RealGDrive implements GDrive {

  private final Drive gDriveService;

  public RealGDrive(GoogleCredential googleCredential) {
    try {
      NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
      JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

      gDriveService = new Drive.Builder(httpTransport, jacksonFactory, googleCredential)
        .setApplicationName("some-app-name")
        .build();
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public List<GDriveFileReference> listFiles() {
    try {
      FileList result =
        gDriveService.files().list()
          .setPageSize(10) // TODO: paginate
          .setFields("files(id, name)")
          .setQ("mimeType = 'application/vnd.google-apps.document'")
          .setOrderBy("modifiedTime desc,name")
          .execute();

      return result.getFiles().stream()
        .map(f -> new GDriveFileReference(f.getId(), f.getName()))
        .collect(toList());
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public String exportToHtml(GDriveFileReference fileReference) {
    try {
      Drive.Files.Export export = gDriveService.files().export(fileReference.id, "text/html");
      return new String(ByteStreams.toByteArray(export.executeMediaAsInputStream()));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
