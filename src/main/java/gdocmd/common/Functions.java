package gdocmd.common;

import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static java.lang.String.format;

public class Functions {
  public static String readFile(String path) {
    checkState(path != null, "expected file path to not be null");
    Path fsPath = FileSystems.getDefault().getPath(path);
    checkState(fsPath.toFile().exists(), format("expected file to exist: '%s'", path));
    try {
      return new String(Files.readAllBytes(fsPath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void checkState(boolean ok, String failureMessage) {
    if (!ok) {
      throw new IllegalStateException(failureMessage);
    }
  }

  public static String joinStringList(String joinWith, List<String> stringsToJoin) {
    StringJoiner stringJoiner = new StringJoiner(joinWith);
    stringsToJoin.forEach(s -> stringJoiner.add(s));
    return stringJoiner.toString();
  }

  public static String mask(String secret) {
    if (secret == null) {
      return "";
    }
    return secret.replaceAll(".{1,1}", "X");
  }

  public static void logInfo(String category, Object obj) {
    Logger.getLogger("gdoc-md-service").info(format("%s: %s", category, obj));
  }
}
