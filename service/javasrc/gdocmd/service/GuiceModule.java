package gdocmd.service;

import com.google.inject.servlet.ServletModule;
import gdocmd.common.config.storage.GcsServicePropertiesStorage;
import gdocmd.common.config.storage.ServicePropertiesStorage;
import gdocmd.gdrive.GDriveSource;
import gdocmd.gdrive.real.RealGDriveSource;

public class GuiceModule extends ServletModule {

  @Override
  protected void configureServlets() {
    bind(ServicePropertiesStorage.class).toInstance(new GcsServicePropertiesStorage());
    bind(GDriveSource.class).to(RealGDriveSource.class);

    serve("/testapp/*").with(MyAppServlet.class);
    serve("/testcron/*").with(MyCronServlet.class);
    serve("/do-oauth-flow").with(OauthAuthorizationCodeServlet.class);
    serve("/fetch-oauth-token").with(FetchOauthTokenServlet.class);
    serve("/").with(AdministrativeServlet.class);
    serve("/save-service-properties").with(AdministrativeServlet.class);
    serve("/test-drive").with(TestDriveServlet.class);
  }
}
