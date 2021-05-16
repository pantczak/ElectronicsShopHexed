package pl.tks.rent_service.infrastructure;


import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;

import java.util.List;
import java.util.UUID;

public interface GetDevicePort {
    Device getDevice(UUID uuid);
    List<Device> getAllDevices();
    List<Laptop> getAllLaptops();
    List<Smartphone> getAllSmartphones();
}
