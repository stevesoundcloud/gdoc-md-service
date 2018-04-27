package gdocmd.htmltomd;

import org.junit.jupiter.api.Test;

import static gdocmd.common.Functions.mapOf;
import static gdocmd.common.Functions.readFile;
import static gdocmd.htmltomd.HtmlToMdConverter.convertHtmlToMd;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScenariosTest {
  @Test
  public void trivial01() {
    assertEquals(
      mapOf("01_trivial.md",
        readFile("scenarios/01_trivial/expected_md/01_trivial.md")),
      convertHtmlToMd(
        mapOf("01_trivial.html",
          readFile("scenarios/01_trivial/actual_gdoc/01_trivial.html"))
      ));
  }

  @Test
  public void trivial02() {
    assertEquals(
      mapOf("02_html_entities.md",
        readFile("scenarios/02_html_entities/expected_md/02_html_entities.md")),
      convertHtmlToMd(
        mapOf("02_html_entities.html",
          readFile("scenarios/02_html_entities/actual_gdoc/02_html_entities.html"))
      ));
  }
}