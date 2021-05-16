package pl.tks.rent_service.model_ent.repositories.interfaces;

import pl.tks.rent_service.model_ent.model.resource.DeviceEnt;
import pl.tks.rent_service.model_ent.model.resource.LaptopEnt;
import pl.tks.rent_service.model_ent.model.resource.SmartphoneEnt;

import java.util.List;
import java.util.UUID;

public interface IDeviceRepository {
    boolean addDevice(DeviceEnt device);
    DeviceEnt getDevice(UUID uuid);
    List<DeviceEnt> getAllDevices();
    void updateDevice(UUID uuid, DeviceEnt newDevice);
    boolean deleteDevice(UUID uuid);
    List<LaptopEnt> getAllLaptops();
    List<SmartphoneEnt> getAllSmartphones();
}
