package pl.tks.rent_service.model_domain.repositories.interfaces;

import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;

import java.util.List;
import java.util.UUID;

public interface IDeviceRepository {
    boolean addDevice(Device device);
    Device getDevice(UUID uuid);
    List<Device> getAllDevices();
    void updateDevice(UUID uuid, Device newDevice);
    boolean deleteDevice(UUID uuid);
    List<Laptop> getAllLaptops();
    List<Smartphone> getAllSmartphones();
}
