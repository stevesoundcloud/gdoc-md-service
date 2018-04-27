package gdocmd.service;

import com.google.inject.servlet.ServletModule;
import gdocmd.gdrive.GDriveSource;
import gdocmd.real.RealGDriveSource;
import gdocmd.storage.GcsStreamingStorage;
import gdocmd.storage.ServicePropertiesStorage;
import gdocmd.storage.GcsServicePropertiesStorage;
import gdocmd.storage.StreamingStorage;

public class GuiceModule extends ServletModule {

  @Override
  protected void configureServlets() {
    bind(ServicePropertiesStorage.class).toInstance(new GcsServicePropertiesStorage());
    bind(StreamingStorage.class).toInstance(new GcsStreamingStorage());
    bind(GDriveSource.class).to(RealGDriveSource.class);

    serve("/testapp/*").with(MyAppServlet.class);
    serve("/testcron/*").with(MyCronServlet.class);
    serve("/").with(AdministrativeServlet.class);
    serve("/save-service-properties").with(AdministrativeServlet.class);
    serve("/test-drive").with(TestDriveServlet.class);
    serve("/convert").with(ConvertServlet.class);
  }
}
