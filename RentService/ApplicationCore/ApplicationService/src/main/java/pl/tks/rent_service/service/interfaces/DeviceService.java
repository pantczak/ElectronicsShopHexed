package pl.tks.rent_service.service.interfaces;

import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface DeviceService extends Serializable {
    Device getDevice(UUID uuid);

    boolean deleteDevice(UUID uuid);

    List<Laptop> getAllLaptops();

    List<Smartphone> getAllSmartphones();

    boolean addLaptop(Laptop laptop);

    boolean addLaptop(String brand, String model, int weightInGrams, int memoryInGb);

    boolean addSmartphone(Smartphone smartphone);

    boolean addSmartphone(String brand, String model, int weightInGrams, double batteryLifetime);

    boolean updateLaptop(Device old, String brand, String model, int weightInGrams, int memoryInGb);

    boolean updateSmartphone(Device old, String brand, String model, int weightInGrams, double batteryLifetime);

    List<Device> getAllDevices();
}
