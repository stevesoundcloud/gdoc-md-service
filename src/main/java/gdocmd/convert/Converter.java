package gdocmd.convert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.LinkedHashMap;
import java.util.Map;

import static gdocmd.common.Functions.checkState;
import static java.lang.String.format;

public class Converter {

  private static final String HTML_FILE_SUFFIX = ".html";
  private static final String MD_FILE_SUFFIX = ".md";

  public static Map<String, String> convertHtmlToMd(Map<String, String> gdocHtmlZipPathToContent) {
    checkState(!gdocHtmlZipPathToContent.isEmpty(), "empty gdoc content");

    Map.Entry<String, String> pathAndHtml = gdocHtmlZipPathToContent.entrySet().iterator().next();
    String htmlPath = pathAndHtml.getKey();
    checkState(htmlPath.endsWith(HTML_FILE_SUFFIX), format("expected '%s' to end in .html", htmlPath));

    String pathWithoutHtml = htmlPath.substring(0, htmlPath.length()-HTML_FILE_SUFFIX.length());
    String mdPath = pathWithoutHtml + MD_FILE_SUFFIX;

    String htmlContent = pathAndHtml.getValue();
    checkState(!htmlContent.isEmpty(), format("html should not be empty, path='%s'", htmlPath));

    Document doc = Jsoup.parse(htmlContent);

    String mdContent = doc.select("span").text();

    // replace non-breaking spaces with regular spaces (scenario 2)
    mdContent = mdContent.replace((char)160, ' ');

    Map<String, String> mdPathToContent = new LinkedHashMap<>();
    mdPathToContent.put(mdPath, mdContent);
    return mdPathToContent;
  }
}