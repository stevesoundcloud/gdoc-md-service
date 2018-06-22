package gdocmd.service;

import convert.GDocsConverter;
import gdocmd.storage.StreamingStorage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static java.lang.String.format;

@Singleton
public class ConvertServlet extends HttpServlet {
  private final GDocsConverter gDocsConverter;
  private final StreamingStorage storage;

  @Inject
  public ConvertServlet(GDocsConverter gDocsConverter, StreamingStorage storage) {
    this.gDocsConverter = gDocsConverter;
    this.storage = storage;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    System.out.println(format("ConvertServlet Cron run: %s", new Date()));

    gDocsConverter.runConversion(new PrintWriter(response.getWriter()), storage);
    response.setStatus(200);
//    response.getWriter().flush();
//    response.getWriter().close();
  }
}
