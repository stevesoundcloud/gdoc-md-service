package gdocmd.common.config.storage;

import java.util.Optional;

public class SimulatedServicePropertiesStorage implements ServicePropertiesStorage {
  public Optional<ServiceProperties> serviceProperties = Optional.empty();

  @Override
  public Optional<ServiceProperties> load() {
    return serviceProperties;
  }

  @Override
  public void save(ServiceProperties serviceProperties) {
    this.serviceProperties = Optional.of(serviceProperties);
  }
}
