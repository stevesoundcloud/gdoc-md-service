package convert;

import gdocmd.gdrive.GDrive;
import gdocmd.gdrive.GDriveFileReference;
import gdocmd.gdrive.GDriveSource;
import gdocmd.storage.StreamingStorage;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static gdocmd.common.Functions.mapOf;
import static gdocmd.htmltomd.HtmlToMdConverter.convertHtmlToMd;
import static java.lang.String.format;

public class GDocsConverter {
  private final GDriveSource gDriveSource;
  private final StreamingStorage storage;

  @Inject
  public GDocsConverter(GDriveSource gDriveSource, StreamingStorage storage) {
    this.gDriveSource = gDriveSource;
    this.storage = storage;
  }

  public void runConversion(PrintWriter logger) {
    Optional<GDrive> maybeGDrive = gDriveSource.maybeLoadGDrive();
    if (!maybeGDrive.isPresent()) {  // TODO: deal with credential not present
      logger.println("No GDrive found, need oauth handshake");
      return;
    }

    GDrive gDrive = maybeGDrive.get();
    List<GDriveFileReference> fileReferences = gDrive.listFiles();
    for (GDriveFileReference ref: fileReferences) {
      System.out.println(ref);
      logger.println(ref);

      logger.println("==========");
      logger.println("==========");
      logger.println("==========");
      String htmlOutput = gDrive.exportToHtml(ref);
      logger.println(convertHtmlToMd(mapOf(format("%s.html", ref.id), htmlOutput)).entrySet().iterator().next().getValue());
    }
  }
}
