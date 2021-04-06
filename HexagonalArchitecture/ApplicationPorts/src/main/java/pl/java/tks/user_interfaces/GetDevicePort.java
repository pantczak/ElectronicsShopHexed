package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;

import java.util.List;
import java.util.UUID;

public interface GetDevicePort {
    Device getDevice(UUID uuid);
    List<Device> getAllDevices();
    List<Laptop> getAllLaptops();
    List<Smartphone> getAllSmartphones();
}
