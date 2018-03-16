package gdocmd.convert;

import java.util.LinkedHashMap;
import java.util.Map;

import static gdocmd.common.Functions.checkState;
import static gdocmd.common.Functions.joinStringList;
import static java.lang.String.format;

class Converter {

  private static final String HTML_FILE_SUFFIX = ".html";
  private static final String MD_FILE_SUFFIX = ".md";
  private static final String META_LINE = "<meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\">";

  public static Map<String, String> convert(Map<String, String> gdocHtmlZipPathToContent) {
    checkState(!gdocHtmlZipPathToContent.isEmpty(), "empty gdoc content");

    Map.Entry<String, String> pathAndHtml = gdocHtmlZipPathToContent.entrySet().iterator().next();
    String htmlPath = pathAndHtml.getKey();
    checkState(htmlPath.endsWith(HTML_FILE_SUFFIX), format("expected '%s' to end in .html", htmlPath));

    String pathWithoutHtml = htmlPath.substring(0, htmlPath.length()-HTML_FILE_SUFFIX.length());
    String mdPath = pathWithoutHtml + MD_FILE_SUFFIX;

    String htmlContent = pathAndHtml.getValue();
    checkState(!htmlContent.isEmpty(), format("html should not be empty, path='%s'", htmlPath));

    // it's not well-formed from an xml perspective.
    // if this is the only case of non-well-form-ed-ness, we're good
    htmlContent = htmlContent.replace(META_LINE, "");

    String mdContent = joinStringList("\n", Functions.xpathList(htmlContent, "//*[@class='c2']"));

    Map<String, String> mdPathToContent = new LinkedHashMap<>();
    mdPathToContent.put(mdPath, mdContent);
    return mdPathToContent;
  }
}