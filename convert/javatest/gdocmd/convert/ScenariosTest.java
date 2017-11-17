package gdocmd.convert;

import org.junit.jupiter.api.Test;

import static gdocmd.convert.Converter.convert;
import static gdocmd.convert.TestUtil.mapOf;
import static gdocmd.convert.Util.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ScenariosTest {
  @Test
  public void trivial01() {
    assertEquals(
      mapOf("gdocmdservice01_trivial.md",
        readFile("scenarios/01_trivial/expected_md/gdocmdservice01_trivial.md")),
      convert(
        mapOf("gdocmdservice01_trivial.html",
          readFile("scenarios/01_trivial/actual_gdoc/gdocmdservice01_trivial.html"))
      ));
  }
}