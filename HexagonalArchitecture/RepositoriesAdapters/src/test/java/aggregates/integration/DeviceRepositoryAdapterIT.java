package aggregates.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.java.tks.aggregates.DeviceRepositoryAdapter;
import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.model_ent.model.resource.LaptopEnt;
import pl.java.tks.model_ent.model.resource.SmartphoneEnt;
import pl.java.tks.model_ent.repositories.DeviceEntRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeviceRepositoryAdapterIT {

    private DeviceRepositoryAdapter adapter;
    private DeviceEntRepository repository;
    //test data
    private final Laptop testLaptop = new Laptop("Macbook", "Pro", 1234, 32);
    //initial data
    private final static int INITIAL_REPOSITORY_SIZE = 2;
    private final SmartphoneEnt initialSmartphone = new SmartphoneEnt("Xiaomi", "Mi10", 600, 5.5);
    private final LaptopEnt initialLaptop = new LaptopEnt("Hp", "ProBook", 1111, 8);

    @BeforeAll
    void initializeRepo() {
        initialLaptop.setUuid(UUID.randomUUID());
        initialSmartphone.setUuid(UUID.randomUUID());
    }

    @BeforeEach
    void initialize() {
        repository = new DeviceEntRepository();
        repository.addDevice(initialSmartphone);
        repository.addDevice(initialLaptop);
        //verify precondition
        assertEquals(INITIAL_REPOSITORY_SIZE, repository.getAllDevices().size());
        adapter = new DeviceRepositoryAdapter(repository);
    }

    @Test
    void addDevice() {
        adapter.addDevice(testLaptop);
        assertEquals(INITIAL_REPOSITORY_SIZE + 1, repository.getAllDevices().size());
    }

    @Test
    void deleteDevice() {
        adapter.deleteDevice(initialLaptop.getUuid());
        assertEquals(INITIAL_REPOSITORY_SIZE - 1, repository.getAllDevices().size());
    }

    @Test
    void getDevice() {
        Laptop device = (Laptop) adapter.getDevice(initialLaptop.getUuid());
        //own
        assertEquals(initialLaptop.getUuid(), device.getUuid());
        assertEquals(initialLaptop.getBrand(), device.getBrand());
        assertEquals(initialLaptop.getModel(), device.getModel());
        assertEquals(initialLaptop.getMemoryInGb(), device.getMemoryInGb());
        assertEquals(initialLaptop.getWeightInGrams(), device.getWeightInGrams());
        assertEquals(initialLaptop.isAvailable(), device.isAvailable());
    }

    @Test
    void getAllDevices() {
        List<Device> list = adapter.getAllDevices();
        assertFalse(list.isEmpty());
        assertEquals(INITIAL_REPOSITORY_SIZE, list.size());
    }

    @Test
    void getAllLaptops() {
        List<Laptop> list = adapter.getAllLaptops();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void getAllSmartphones() {
        List<Smartphone> list = adapter.getAllSmartphones();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void updateDevice() {
        adapter.updateDevice(initialLaptop.getUuid(), testLaptop);
        assertEquals(testLaptop.getModel(), repository.getDevice(initialLaptop.getUuid()).getModel());
        assertEquals(testLaptop.getBrand(), repository.getDevice(initialLaptop.getUuid()).getBrand());
    }
}