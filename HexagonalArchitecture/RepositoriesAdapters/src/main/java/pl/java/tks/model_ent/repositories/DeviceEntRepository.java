package pl.java.tks.model_ent.repositories;


import pl.java.tks.model_ent.model.resource.DeviceEnt;
import pl.java.tks.model_ent.model.resource.LaptopEnt;
import pl.java.tks.model_ent.model.resource.SmartphoneEnt;
import pl.java.tks.model_ent.repositories.interfaces.IDeviceRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DeviceEntRepository implements IDeviceRepository, Serializable {

    private final List<DeviceEnt> devices;

    public DeviceEntRepository() {
        this.devices = new ArrayList<>();
    }

    @Override
    public boolean addDevice(DeviceEnt device) {
        synchronized (devices) {
            device.setUuid(UUID.randomUUID());
            return devices.add(device);
        }
    }

    @Override
    public DeviceEnt getDevice(UUID uuid) {
        synchronized (devices) {
            return devices.stream().filter(d -> d.getUuid().equals(uuid)).findFirst().orElse(null);
        }
    }


    @Override
    public List<DeviceEnt> getAllDevices() {
        synchronized (devices) {
            return new ArrayList<>(devices);
        }

    }

    @Override
    public void updateDevice(UUID uuid, DeviceEnt newDevice) {
        synchronized (devices) {
            for (DeviceEnt r : devices) {
                if (r.getUuid().equals(uuid)) {
                    newDevice.setUuid(uuid);
                    devices.set(devices.indexOf(r), newDevice);
                }
            }
        }
    }

    @Override
    public boolean deleteDevice(UUID uuid) {
        synchronized (devices) {
            return devices.remove(getDevice(uuid));
        }
    }

    @Override
    public List<LaptopEnt> getAllLaptops() {
        synchronized (devices) {
            List<LaptopEnt> laptops = new ArrayList<>();
            for (DeviceEnt d : devices) {
                if (d instanceof LaptopEnt) {
                    laptops.add((LaptopEnt) d);
                }
            }
            return laptops;
        }
    }

    @Override
    public List<SmartphoneEnt> getAllSmartphones() {
        synchronized (devices) {
            List<SmartphoneEnt> smartphones = new ArrayList<>();
            for (DeviceEnt d : devices) {
                if (d instanceof SmartphoneEnt) {
                    smartphones.add((SmartphoneEnt) d);
                }
            }
            return smartphones;
        }
    }
}
