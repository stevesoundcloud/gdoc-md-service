package gdocmd.service;

import gdocmd.gdrive.GDriveFileReference;
import gdocmd.gdrive.GDriveSource;
import gdocmd.gdrive.GDrive;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    response.getWriter().println(gDrive.exportToHtml(fileReferences.get(0)));
  }
}
