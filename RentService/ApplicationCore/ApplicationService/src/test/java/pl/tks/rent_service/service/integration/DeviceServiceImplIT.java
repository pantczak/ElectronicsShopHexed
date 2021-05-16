package pl.tks.rent_service.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.tks.rent_service.aggregates.DeviceRepositoryAdapter;
import pl.tks.rent_service.aggregates.EventRepositoryAdapter;

import pl.tks.rent_service.model_ent.repositories.DeviceEntRepository;
import pl.tks.rent_service.model_ent.repositories.EventEntRepository;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.service.DeviceServiceImpl;
import pl.tks.rent_service.service.interfaces.DeviceService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeviceServiceImplIT {

    private DeviceService service;

    private final Laptop testLaptop = new Laptop("Macbook", "Pro", 1499, 32);
    private final Smartphone testSmartphone = new Smartphone("Xiaomi", "Mi10", 450, 9.9);

    @BeforeEach
    public void initialize() {
        testLaptop.setUuid(UUID.randomUUID());
        testSmartphone.setUuid(UUID.randomUUID());


        service = new DeviceServiceImpl(new DeviceRepositoryAdapter(new DeviceEntRepository()),
                new EventRepositoryAdapter(new EventEntRepository()));

        service.addLaptop(testLaptop);
        service.addSmartphone(testSmartphone);


    }

    @Test
    void getDevice() {
        Device device = service.getDevice(service.getAllDevices().get(0).getUuid());
        assertEquals(testLaptop.getBrand(), device.getBrand());
        assertEquals(testLaptop.getModel(), device.getModel());
        assertEquals(testLaptop.getWeightInGrams(), device.getWeightInGrams());
    }

    @Test
    void deleteDevice() {
        int size = service.getAllDevices().size();
        service.deleteDevice(service.getAllDevices().get(0).getUuid());
        assertEquals(size - 1, service.getAllDevices().size());
    }

    @Test
    void getAllLaptops() {
        assertEquals(1, service.getAllLaptops().size());
    }

    @Test
    void getAllSmartphones() {
        assertEquals(1, service.getAllSmartphones().size());
    }

    @Test
    void addLaptop() {
        int size = service.getAllLaptops().size();
        service.addLaptop(testLaptop);
        assertEquals(size + 1, service.getAllLaptops().size());

    }

    @Test
    void addLaptopByParams() {
        int size = service.getAllLaptops().size();
        service.addLaptop(testLaptop.getBrand(), testLaptop.getModel(), testLaptop.getWeightInGrams(), testLaptop.getMemoryInGb());
        assertEquals(size + 1, service.getAllLaptops().size());
    }

    @Test
    void addSmartphone() {
        int size = service.getAllSmartphones().size();
        service.addSmartphone(testSmartphone);
        assertEquals(size + 1, service.getAllSmartphones().size());
    }

    @Test
    void addSmartphoneByParams() {
        int size = service.getAllSmartphones().size();
        service.addSmartphone(testSmartphone.getBrand(), testSmartphone.getModel(),
                testSmartphone.getWeightInGrams(), testSmartphone.getBatteryLifetime());
        assertEquals(size + 1, service.getAllSmartphones().size());
    }

    @Test
    void updateLaptop() {
        Laptop update = new Laptop("Update", "Update", 1499, 32);
        Laptop laptop = (Laptop) service.getDevice(service.getAllDevices().get(0).getUuid());
        service.updateLaptop(laptop, update.getBrand(), update.getModel(), update.getWeightInGrams(), update.getMemoryInGb());
        assertEquals(update.getBrand(), service.getDevice(service.getAllDevices().get(0).getUuid()).getBrand());
    }

    @Test
    void updateSmartphone() {
        Smartphone update = new Smartphone("Update", "Update", 1499, 32);
        Smartphone smartphone = (Smartphone) service.getDevice(service.getAllDevices().get(1).getUuid());
        service.updateSmartphone(smartphone, update.getBrand(), update.getModel(), update.getWeightInGrams(), update.getWeightInGrams());
        assertEquals(update.getBrand(), service.getDevice(service.getAllDevices().get(1).getUuid()).getBrand());
    }

    @Test
    void getAllDevices() {
        assertEquals(2, service.getAllDevices().size());
    }
}