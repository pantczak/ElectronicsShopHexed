package pl.java.tks.aggregates;

import pl.java.tks.aggregates.converters.DeviceConverter;
import pl.java.tks.infrastructure.DevicePort;
import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.model_ent.repositories.DeviceEntRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class DeviceRepositoryAdapter implements DevicePort {

    private final DeviceEntRepository repository;
//    private ModelEntConverter converter;

    @Inject
    public DeviceRepositoryAdapter(DeviceEntRepository repository) {
        this.repository = repository;
//        converter = new ModelEntConverter();
    }

    @Override
    public boolean addDevice(Device device) {
        return repository.addDevice(DeviceConverter.convertDeviceToEnt(device));
    }

    @Override
    public boolean deleteDevice(UUID uuid) {
        return repository.deleteDevice(uuid);
    }

    @Override
    public Device getDevice(UUID uuid) {
        return DeviceConverter.convertEntToDevice(repository.getDevice(uuid));
    }

    @Override
    public List<Device> getAllDevices() {
//        List<Device> devices = new LinkedList<>();
//        for (DeviceEnt deviceEnt : repository.getAllDevices()) {
//            devices.add(DeviceConverter.convertEntToDevice(deviceEnt));
//        }
//        return devices;

        return repository.getAllDevices().stream().map(DeviceConverter::convertEntToDevice).collect(Collectors.toList());
    }

    @Override
    public List<Laptop> getAllLaptops() {
//        List<Laptop> laptops = new LinkedList<>();
//        for (LaptopEnt laptopEnt : repository.getAllLaptops()) {
//            laptops.add((Laptop) DeviceConverter.convertEntToDevice(laptopEnt));
//        }
//        return laptops;

        return repository.getAllLaptops().stream().map(DeviceConverter::convertEntToDevice).map(Laptop.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<Smartphone> getAllSmartphones() {
//        List<Smartphone> smartphones = new LinkedList<>();
//        for (SmartphoneEnt smartphoneEnt : repository.getAllSmartphones()) {
//            smartphones.add((Smartphone) DeviceConverter.convertEntToDevice(smartphoneEnt));
//        }
//        return smartphones;
        return repository.getAllSmartphones().stream().map(DeviceConverter::convertEntToDevice).map(Smartphone.class::cast).collect(Collectors.toList());
    }

    @Override
    public void updateDevice(UUID uuid, Device newDevice) {
        repository.updateDevice(uuid, DeviceConverter.convertDeviceToEnt(newDevice));

    }


}
