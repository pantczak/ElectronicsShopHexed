package pl.tks.rent_service.service;

import pl.tks.rent_service.infrastructure.DevicePort;
import pl.tks.rent_service.infrastructure.EventPort;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.service.interfaces.DeviceService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DeviceServiceImpl implements DeviceService {

    private DevicePort devicePort;

    private EventPort eventPort;

    @Inject
    public DeviceServiceImpl(DevicePort devicePort, EventPort eventPort) {
        this.devicePort = devicePort;
        this.eventPort = eventPort;
    }

    public DeviceServiceImpl() {
    }

    @Override
    public Device getDevice(UUID uuid) {
        return devicePort.getDevice(uuid);
    }

    @Override
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

    @Override
    public List<Laptop> getAllLaptops() {
        return devicePort.getAllLaptops();
    }

    @Override
    public List<Smartphone> getAllSmartphones() {
        return devicePort.getAllSmartphones();
    }

    @Override
    public boolean addLaptop(Laptop laptop) {
        return addLaptop(laptop.getBrand(), laptop.getModel(), laptop.getWeightInGrams(), laptop.getMemoryInGb());
    }

    @Override
    public boolean addLaptop(String brand, String model, int weightInGrams, int memoryInGb) {
        if (brand == null || model == null || weightInGrams <= 0 || memoryInGb <= 0) {
            return false;
        }
        return devicePort.addDevice(new Laptop(brand, model, weightInGrams, memoryInGb));
    }

    @Override
    public boolean addSmartphone(Smartphone smartphone) {
        return addSmartphone(smartphone.getBrand(), smartphone.getModel(), smartphone.getWeightInGrams(), smartphone.getBatteryLifetime());
    }

    @Override
    public boolean addSmartphone(String brand, String model, int weightInGrams, double batteryLifetime) {
        if (brand == null || model == null || weightInGrams <= 0 || batteryLifetime <= 0) {
            return false;
        }
        return devicePort.addDevice(new Smartphone(brand, model, weightInGrams, batteryLifetime));
    }

    @Override
    public boolean updateLaptop(Device old, String brand, String model, int weightInGrams, int memoryInGb) {
        if (old == null || brand == null || model == null || weightInGrams <= 0 || memoryInGb <= 0 ||
                devicePort.getAllDevices().stream().noneMatch(device -> device.getUuid().equals(old.getUuid())) ||
                        !(old instanceof Laptop) || !old.isAvailable()) {
            return false;
        }
        devicePort.updateDevice(old.getUuid(), new Laptop(brand, model, weightInGrams, memoryInGb));
        return true;
    }

    @Override
    public boolean updateSmartphone(Device old, String brand, String model, int weightInGrams, double batteryLifetime) {
        if (old == null || brand == null || model == null || weightInGrams <= 0 || batteryLifetime <= 0 ||
                devicePort.getAllDevices().stream().noneMatch(device -> device.getUuid().equals(old.getUuid())) ||
                !(old instanceof Smartphone) || !old.isAvailable()) {
            return false;
        }
        devicePort.updateDevice(old.getUuid(), new Smartphone(brand, model, weightInGrams, batteryLifetime));
        return true;
    }

    @Override
    public List<Device> getAllDevices() {
        return devicePort.getAllDevices();
    }


}
