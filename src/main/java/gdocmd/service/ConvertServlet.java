package gdocmd.service;

import gdocmd.gdrive.GDrive;
import gdocmd.gdrive.GDriveFileReference;
import gdocmd.gdrive.GDriveSource;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static gdocmd.common.Functions.mapOf;
import static gdocmd.convert.Converter.convertHtmlToMd;
import static java.lang.String.format;

@Singleton
public class ConvertServlet extends HttpServlet {
  private final GDriveSource gDriveSource;

  @Inject
  public ConvertServlet(GDriveSource gDriveSource) {
    this.gDriveSource = gDriveSource;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println(format("ConvertServlet Cron run: %s", new Date()));

    Optional<GDrive> maybeGDrive = gDriveSource.maybeLoadGDrive();
    if (!maybeGDrive.isPresent()) {  // TODO: deal with credential not present
      response.getWriter().println("No GDrive found, need oauth handshake");
      return;
    }

    GDrive gDrive = maybeGDrive.get();
    List<GDriveFileReference> fileReferences = gDrive.listFiles();
    for (GDriveFileReference ref: fileReferences) {
      System.out.println(ref);
      response.getWriter().println(ref);

      response.getWriter().println("==========");
      response.getWriter().println("==========");
      response.getWriter().println("==========");
      String htmlOutput = gDrive.exportToHtml(ref);
      response.getWriter().println(convertHtmlToMd(mapOf("foo.html", htmlOutput)).entrySet().iterator().next().getValue());
    }
  }
}
