package gdocmd.service;

import convert.GDocsConverter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

@Singleton
public class ConvertServlet extends HttpServlet {
  private final GDocsConverter gDocsConverter;

  @Inject
  public ConvertServlet(GDocsConverter gDocsConverter) {
    this.gDocsConverter = gDocsConverter;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    System.out.println(format("ConvertServlet Cron run: %s", new Date()));

    gDocsConverter.runConversion(response.getWriter());
  }
}
