package pl.tks.rent_service.rest.aggregates.adapters;


import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.rest.aggregates.mappers.DeviceMapper;
import pl.tks.rent_service.rest.model.DeviceDtoRest;
import pl.tks.rent_service.service.interfaces.DeviceService;
import pl.tks.rent_service.user_interfaces.AddDevicePort;
import pl.tks.rent_service.user_interfaces.DeleteDevicePort;
import pl.tks.rent_service.user_interfaces.GetDevicePort;


import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class DeviceRestAdapter implements GetDevicePort<DeviceDtoRest, DeviceDtoRest, DeviceDtoRest>, DeleteDevicePort, AddDevicePort<DeviceDtoRest> {

    @Inject
    private DeviceMapper mapper;

    private final DeviceService service;

    @Inject
    public DeviceRestAdapter(DeviceService service) {
        this.service = service;
    }

    @Override
    public DeviceDtoRest getDevice(UUID uuid) {
        Device device = service.getDevice(uuid);
        if (device instanceof Laptop) {
            return mapper.laptopToDeviceDto((Laptop) device);
        }
        return mapper.smartphoneToDeviceDto((Smartphone) device);
    }

    @Override
    public List<DeviceDtoRest> getAllDevices() {
        List<DeviceDtoRest> devices = new ArrayList<>();
        for (Device device : service.getAllDevices()) {
            if (device instanceof Laptop) {
                devices.add(mapper.laptopToDeviceDto((Laptop) device));
            } else {
                devices.add(mapper.smartphoneToDeviceDto((Smartphone) device));
            }

        }
        return devices;
    }

    @Override
    public List<DeviceDtoRest> getAllLaptops() {
        return service.getAllLaptops().stream().map(mapper::laptopToDeviceDto).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDtoRest> getAllSmartphones() {
        return service.getAllSmartphones().stream().map(mapper::smartphoneToDeviceDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteDevice(UUID uuid) {
        return service.deleteDevice(uuid);
    }


    @Override
    public boolean addSmartphone(DeviceDtoRest smartphone) {
        return service.addSmartphone(mapper.toSmartphone(smartphone));
    }

    @Override
    public boolean addLaptop(DeviceDtoRest laptop) {
        return service.addLaptop(mapper.toLaptop(laptop));
    }
}
