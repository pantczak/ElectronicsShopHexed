package pl.java.tks.soap.aggregates.adapters;

import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.model_ent.model.resource.SmartphoneEnt;
import pl.java.tks.service.DeviceService;
import pl.java.tks.soap.aggregates.mappers.DeviceMapper;
import pl.java.tks.soap.model.DeviceDtoSoap;
import pl.java.tks.user_interfaces.GetDevicePort;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class DeviceSoapAdapter implements GetDevicePort<DeviceDtoSoap, DeviceDtoSoap, DeviceDtoSoap> {

    private final DeviceService service;

    @Inject
    public DeviceSoapAdapter(DeviceService service) {
        this.service = service;
    }

    @Override
    public DeviceDtoSoap getDevice(UUID uuid) {
        Device device = service.getDevice(uuid);
        if (device instanceof Laptop) {
            return DeviceMapper.INSTANCE.laptopToDeviceDto((Laptop) device);
        }
        return DeviceMapper.INSTANCE.smartphoneToDeviceDto((Smartphone) device);
    }

    @Override
    public List<DeviceDtoSoap> getAllDevices() {
        List<DeviceDtoSoap> devices = new ArrayList<>();
        for (Device device : service.getAllDevices()) {
            if (device instanceof Laptop) {
                devices.add(DeviceMapper.INSTANCE.laptopToDeviceDto((Laptop) device));
            } else {
                devices.add(DeviceMapper.INSTANCE.smartphoneToDeviceDto((Smartphone) device));
            }

        }
        return devices;
    }

    @Override
    public List<DeviceDtoSoap> getAllLaptops() {
        return service.getAllLaptops().stream().map(DeviceMapper.INSTANCE::laptopToDeviceDto).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDtoSoap> getAllSmartphones() {
        return service.getAllSmartphones().stream().map(DeviceMapper.INSTANCE::smartphoneToDeviceDto).collect(Collectors.toList());
    }
}
