package gdocmd.common.config.storage;

import java.util.Optional;

public interface ServicePropertiesStorage {
  Optional<ServiceProperties> load();
  void save(ServiceProperties serviceProperties);
}
