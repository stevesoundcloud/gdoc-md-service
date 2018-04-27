package gdocmd.storage;

import com.google.common.base.Throwables;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;
import java.util.Properties;

import static gdocmd.common.Functions.logInfo;
import static gdocmd.storage.GcsFunctions.readFromGcs;
import static gdocmd.storage.GcsFunctions.saveToGcs;

public class GcsServicePropertiesStorage implements ServicePropertiesStorage {
  private static final String SERVICE_DOT_PROPERTIES = "service.properties";

  private static void oauthLogInfo(Object obj) {
    logInfo("SERVICE CONFIG", obj);
  }

  @Override
  public Optional<ServiceProperties> load() {
    Optional<String> maybeFileContent = readFromGcs(SERVICE_DOT_PROPERTIES);
    if (!maybeFileContent.isPresent()) {
      return Optional.empty();
    }
    try {
      Properties existingProperties = new Properties();
      existingProperties.load(new StringReader(maybeFileContent.get()));
      ServiceProperties sp = new ServiceProperties();
      sp.endUserOauthAccessToken = existingProperties.getProperty(ServiceProperties.END_USER_OAUTH_ACCESS_TOKEN);
      sp.endUserOauthRefreshToken = existingProperties.getProperty(ServiceProperties.END_USER_OAUTH_REFRESH_TOKEN);
      return Optional.of(sp);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void save(ServiceProperties serviceProperties) {
    try {
      ByteArrayOutputStream ostream = new ByteArrayOutputStream();
      Properties properties = serviceProperties.asJavaUtilProperties();
      properties.store(ostream, "Config, including secrets, used by gdoc-md-service");
      ostream.flush();
      ostream.close();

      oauthLogInfo(serviceProperties);

      saveToGcs(SERVICE_DOT_PROPERTIES, "text/plain", ostream.toByteArray());
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
