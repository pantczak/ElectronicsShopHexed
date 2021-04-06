package pl.java.tks.service;

import pl.java.tks.infrastructure.DevicePort;
import pl.java.tks.infrastructure.EventPort;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DeviceService implements Serializable {

    private DevicePort devicePort;

    private EventPort eventPort;

    @Inject
    public DeviceService(DevicePort devicePort, EventPort eventPort) {
        this.devicePort = devicePort;
        this.eventPort = eventPort;
    }

    public DeviceService() {
    }

    public Device getDevice(UUID uuid) {
        return devicePort.getDevice(uuid);
    }

    public boolean deleteDevice(UUID uuid) {
        if (devicePort.getDevice(uuid) == null) {
            return false;
        }
        if (!devicePort.getDevice(uuid).isAvailable()) {
            return false;
        }
        for (Event event : eventPort.getAllEvents()) {
            if (event.getDevice() == devicePort.getDevice(uuid)) {
                event.setDevice(null);
            }
        }
        return devicePort.deleteDevice(uuid);
    }

    public List<Laptop> getAllLaptops() {
        return devicePort.getAllLaptops();
    }

    public List<Smartphone> getAllSmartphones() {
        return devicePort.getAllSmartphones();
    }

    public boolean addLaptop(Laptop laptop) {
        return addLaptop(laptop.getBrand(), laptop.getModel(), laptop.getWeightInGrams(), laptop.getMemoryInGb());
    }

    public boolean addLaptop(String brand, String model, int weightInGrams, int memoryInGb) {
        if (brand == null || model == null || weightInGrams <= 0 || memoryInGb <= 0) {
            return false;
        }
        return devicePort.addDevice(new Laptop(brand, model, weightInGrams, memoryInGb));
    }

    public boolean addSmartphone(Smartphone smartphone) {
        return addSmartphone(smartphone.getBrand(), smartphone.getModel(), smartphone.getWeightInGrams(), smartphone.getBatteryLifetime());
    }

    public boolean addSmartphone(String brand, String model, int weightInGrams, double batteryLifetime) {
        if (brand == null || model == null || weightInGrams <= 0 || batteryLifetime <= 0) {
            return false;
        }
        return devicePort.addDevice(new Smartphone(brand, model, weightInGrams, batteryLifetime));
    }

    public boolean updateLaptop(Device old, String brand, String model, int weightInGrams, int memoryInGb) {
        if (old == null || brand == null || model == null || weightInGrams <= 0 || memoryInGb <= 0 ||
                devicePort.getAllDevices().stream().noneMatch(device -> device.getUuid().equals(old.getUuid())) ||
                        !(old instanceof Laptop) || !old.isAvailable()) {
            return false;
        }
        devicePort.updateDevice(old.getUuid(), new Laptop(brand, model, weightInGrams, memoryInGb));
        return true;
    }

    public boolean updateSmartphone(Device old, String brand, String model, int weightInGrams, double batteryLifetime) {
        if (old == null || brand == null || model == null || weightInGrams <= 0 || batteryLifetime <= 0 ||
                devicePort.getAllDevices().stream().noneMatch(device -> device.getUuid().equals(old.getUuid())) ||
                !(old instanceof Smartphone) || !old.isAvailable()) {
            return false;
        }
        devicePort.updateDevice(old.getUuid(), new Smartphone(brand, model, weightInGrams, batteryLifetime));
        return true;
    }

    public List<Device> getAllDevices() {
        return devicePort.getAllDevices();
    }


}
