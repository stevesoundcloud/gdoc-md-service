package gdocmd.common;


import com.google.api.client.util.IOUtils;
import com.google.api.client.util.Throwables;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

  public static void copyStream(InputStream in, OutputStream out) {
    try {
      IOUtils.copy(in, out);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }


  public static <K, V> Map<K, V> emptyMap() {
    return Collections.unmodifiableMap(new HashMap<>());
  }

  public static <K, V> Map<K, V> mapOf(
    K k1, V v1) {
    Map<K, V> m = new LinkedHashMap<>();
    m.put(k1, v1);
    return Collections.unmodifiableMap(m);
  }

  public static <K, V> Map<K, V> mapOf(
    K k1, V v1,
    K k2, V v2) {
    Map<K, V> m = new LinkedHashMap<>();
    m.put(k1, v1);
    m.put(k2, v2);
    return Collections.unmodifiableMap(m);
  }

  public static <K, V> Map<K, V> mapOf(
    K k1, V v1,
    K k2, V v2,
    K k3, V v3) {
    Map<K, V> m = new LinkedHashMap<>();
    m.put(k1, v1);
    m.put(k2, v2);
    m.put(k3, v3);
    return Collections.unmodifiableMap(m);
  }

  public static <K, V> Map<K, V> mapOf(
    K k1, V v1,
    K k2, V v2,
    K k3, V v3,
    K k4, V v4) {
    Map<K, V> m = new LinkedHashMap<>();
    m.put(k1, v1);
    m.put(k2, v2);
    m.put(k3, v3);
    m.put(k4, v4);
    return Collections.unmodifiableMap(m);
  }

  public static <K, V> Map<K, V> mapOf(
    K k1, V v1,
    K k2, V v2,
    K k3, V v3,
    K k4, V v4,
    K k5, V v5) {
    Map<K, V> m = new LinkedHashMap<>();
    m.put(k1, v1);
    m.put(k2, v2);
    m.put(k3, v3);
    m.put(k4, v4);
    m.put(k5, v5);
    return Collections.unmodifiableMap(m);
  }

  public static <K, V> Map<K, V> mapOf(
    K k1, V v1,
    K k2, V v2,
    K k3, V v3,
    K k4, V v4,
    K k5, V v5,
    K k6, V v6) {
    Map<K, V> m = new LinkedHashMap<>();
    m.put(k1, v1);
    m.put(k2, v2);
    m.put(k3, v3);
    m.put(k4, v4);
    m.put(k5, v5);
    m.put(k6, v6);
    return Collections.unmodifiableMap(m);
  }
}
