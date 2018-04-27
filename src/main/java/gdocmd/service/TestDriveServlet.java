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
import java.util.List;
import java.util.Optional;

import static gdocmd.common.Functions.mapOf;
import static gdocmd.htmltomd.HtmlToMdConverter.convertHtmlToMd;

@Singleton
public class TestDriveServlet extends HttpServlet {

  private final GDriveSource gDriveSource;

  @Inject
  public TestDriveServlet(GDriveSource gDriveSource) {
    this.gDriveSource = gDriveSource;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    }

    response.getWriter().println("==========");
    response.getWriter().println("==========");
    response.getWriter().println("==========");
    String htmlOutput = gDrive.exportToHtml(fileReferences.get(0));
    response.getWriter().println(htmlOutput);
    response.getWriter().println("==========");
    response.getWriter().println("==========");
    response.getWriter().println("==========");
    response.getWriter().println(convertHtmlToMd(mapOf("foo.html", htmlOutput)).entrySet().iterator().next().getValue());
  }
}
