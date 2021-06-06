package pl.tks.rent_service.aggregates;

import pl.tks.rent_service.aggregates.converters.DeviceConverter;
import pl.tks.rent_service.infrastructure.DevicePort;
import pl.tks.rent_service.model_ent.repositories.DeviceEntRepository;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;

import javax.annotation.PostConstruct;
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

    @PostConstruct
    private void initRepo(){
        addDevice(new Laptop("Apple","MacBook",1660,16));
        addDevice(new Laptop("Xiaomi","MiAir",1290,8));
        addDevice(new Laptop("HP","ProBook",1440,8));
        addDevice(new Smartphone("Samsung","S29",1440,15.6));
        addDevice(new Smartphone("Nokia","460",600,119.9));
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
