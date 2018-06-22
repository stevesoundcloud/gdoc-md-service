package convert;

import gdocmd.gdrive.GDrive;
import gdocmd.gdrive.GDriveFileReference;
import gdocmd.gdrive.GDriveSource;
import gdocmd.storage.GcsFunctions.TarEntry;
import gdocmd.storage.StreamingStorage;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import static gdocmd.common.Functions.mapOf;
import static gdocmd.htmltomd.HtmlToMdConverter.convertHtmlToMd;
import static gdocmd.storage.GcsFunctions.streamUploadTarGz;
import static java.lang.String.format;

public class GDocsConverter {
  private final GDriveSource gDriveSource;
  private final StreamingStorage storage;

  @Inject
  public GDocsConverter(GDriveSource gDriveSource, StreamingStorage storage) {
    this.gDriveSource = gDriveSource;
    this.storage = storage;
  }

  public void runConversion(PrintWriter logger, StreamingStorage storage) {
    Optional<GDrive> maybeGDrive = gDriveSource.maybeLoadGDrive();
    if (!maybeGDrive.isPresent()) {  // TODO: deal with credential not present
      logger.println("No GDrive found, need oauth handshake");
      return;
    }

    GDrive gDrive = maybeGDrive.get();
    List<GDriveFileReference> fileReferences = gDrive.listFiles();
    Iterable<TarEntry> tarEntryIterable = fileReferences.stream()
      .map(gdriveFileReference -> {
        System.out.println(gdriveFileReference);
        logger.println(gdriveFileReference);

        logger.println("==========");
        logger.println("==========");
        logger.println("==========");
        String htmlOutput = gDrive.exportToHtml(gdriveFileReference);
        String mdOutput = convertHtmlToMd(mapOf(format("%s.html", gdriveFileReference.id), htmlOutput)).entrySet().iterator().next().getValue();
        logger.println(mdOutput);

        return new TarEntry(gdriveFileReference.id, mdOutput.getBytes());
      })::iterator;

    streamUploadTarGz("test.tar.gz", storage, tarEntryIterable);
  }
}
