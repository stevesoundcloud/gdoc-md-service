package gdocmd.common.config.storage;

import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.common.base.Throwables;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.Properties;

import static gdocmd.common.Functions.envVarValue;
import static gdocmd.common.Functions.logInfo;
import static gdocmd.common.config.storage.ServiceProperties.APP_OAUTH_CLIENT_ID;
import static gdocmd.common.config.storage.ServiceProperties.APP_OAUTH_CLIENT_SECRET;
import static gdocmd.common.config.storage.ServiceProperties.END_USER_OAUTH_ACCESS_TOKEN;
import static gdocmd.common.config.storage.ServiceProperties.END_USER_OAUTH_REQUEST_TOKEN;
import static gdocmd.common.config.storage.ServiceProperties.GCS_OUTBOX_BUCKET_NAME;
import static java.lang.String.format;

public class GcsServicePropertiesStorage implements ServicePropertiesStorage {
  private static final String SERVICE_PROPERTIES_STORAGE_BUCKET_NAME = envVarValue("SERVICE_PROPERTIES_STORAGE_BUCKET_NAME");
  private static final String SERVICE_DOT_PROPERTIES = "service.properties";
  private static final GcsFilename SERVICE_DOT_PROPERTIES_GCS_FILE = new GcsFilename(SERVICE_PROPERTIES_STORAGE_BUCKET_NAME, SERVICE_DOT_PROPERTIES);

  private static void oauthLogInfo(Object obj) {
    logInfo("SERVICE CONFIG", obj);
  }

  @Override
  public Optional<ServiceProperties> load() {
    Optional<String> maybeFileContent = readGcsFile(SERVICE_DOT_PROPERTIES_GCS_FILE);
    if (!maybeFileContent.isPresent()) {
      return Optional.empty();
    }
    try {
      Properties existingProperties = new Properties();
      existingProperties.load(new StringReader(maybeFileContent.get()));
      ServiceProperties sp = new ServiceProperties();
      sp.appOauthClientId = existingProperties.getProperty(APP_OAUTH_CLIENT_ID);
      sp.appOauthClientSecret = existingProperties.getProperty(APP_OAUTH_CLIENT_SECRET);
      sp.endUserOauthAccessToken = existingProperties.getProperty(END_USER_OAUTH_ACCESS_TOKEN);
      sp.endUserOauthRefreshToken = existingProperties.getProperty(END_USER_OAUTH_REQUEST_TOKEN);
      sp.gcsOutboxBucketName = existingProperties.getProperty(GCS_OUTBOX_BUCKET_NAME);
      return Optional.of(sp);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  @Override
  public void save(ServiceProperties serviceProperties) {
    try {
      GcsService gcsService = GcsServiceFactory.createGcsService();
      ByteArrayOutputStream ostream = new ByteArrayOutputStream();
      Properties properties = serviceProperties.asJavaUtilProperties();
      properties.store(ostream, "Config, including secrets, used by gdoc-md-service");
      ostream.flush();
      ostream.close();

      oauthLogInfo(serviceProperties);

      gcsService.createOrReplace(
        SERVICE_DOT_PROPERTIES_GCS_FILE,
        new GcsFileOptions.Builder()
          .mimeType("text/plain")
          .build(),
        ByteBuffer.wrap(ostream.toByteArray()));
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static Optional<String> readGcsFile(GcsFilename gcsFile) {
    try {
      GcsService gcsService = GcsServiceFactory.createGcsService();

      oauthLogInfo(format("Read from GCS: bucketName=%s objectName=%s",
        gcsFile.getBucketName(), gcsFile.getObjectName()));
      GcsInputChannel gcsInputChannel = gcsService.openReadChannel(gcsFile, 0);
      StringBuffer sb = new StringBuffer();
      int readResult = 0;
      do {
        ByteBuffer buffer = ByteBuffer.allocate(10000);
        readResult = gcsInputChannel.read(buffer);
        sb.append(new String(buffer.array()));
      }
      while (readResult != -1);
      return Optional.of(sb.toString());
    } catch (FileNotFoundException fex) {
      return Optional.empty();
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }
}
