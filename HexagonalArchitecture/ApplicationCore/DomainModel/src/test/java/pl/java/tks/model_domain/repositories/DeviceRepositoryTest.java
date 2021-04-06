package pl.java.tks.model_domain.repositories;

import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceRepositoryTest {

    private final static int REPOSITORY_SIZE = 3;
    private final static int SMARTPHONE_COUNT = 1;
    private final static int LAPTOP_COUNT = 2;

    private DeviceRepository repository;
    private final Laptop laptop = new Laptop("Samsung", "L20", 1266, 8);
    private final Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 400, 6.6);
    private final Laptop laptop1 = new Laptop("Apple", "Macbook", 866, 32);

    @BeforeEach
    void initialize() {
        repository = new DeviceRepository();
        assertTrue(repository.getAllDevices().isEmpty());
        repository.addDevice(laptop);
        repository.addDevice(smartphone);
        repository.addDevice(laptop1);
    }

    @Test
    void testAddDevice() {
        final int DEVICE_COUNT = repository.getAllDevices().size();
        Smartphone test = new Smartphone("Test", "Tested", 666, 6.66);
        repository.addDevice(test);
        assertEquals(DEVICE_COUNT + 1, repository.getAllDevices().size());
    }

    @Test
    void testGetDevice() {
        assertEquals(smartphone, repository.getDevice(smartphone.getUuid()));
    }

    @Test
    void testGetAllDevices() {
        assertEquals(REPOSITORY_SIZE, repository.getAllDevices().size());
    }

    @Test
    void testUpdateDevice() {
        Smartphone test = new Smartphone("Update", "Update", 666, 6.66);
        repository.updateDevice(smartphone.getUuid(), test);
        assertEquals(smartphone.getUuid(), test.getUuid());
    }

    @Test
    void testDeleteDevice() {
        repository.deleteDevice(smartphone.getUuid());
        assertEquals(REPOSITORY_SIZE - 1, repository.getAllDevices().size());
    }

    @Test
    void testGetAllLaptops() {
        assertEquals(LAPTOP_COUNT, repository.getAllLaptops().size());
    }

    @Test
    void testGetAllSmartphones() {
        assertEquals(SMARTPHONE_COUNT, repository.getAllSmartphones().size());
    }
}