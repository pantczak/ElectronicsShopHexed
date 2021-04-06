package pl.java.tks.model_ent.repositories;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.java.tks.model_ent.model.resource.LaptopEnt;
import pl.java.tks.model_ent.model.resource.SmartphoneEnt;

import static org.junit.jupiter.api.Assertions.*;


class DeviceEntRepositoryTest {

    private final static int REPOSITORY_SIZE = 3;
    private final static int SMARTPHONE_COUNT = 1;
    private final static int LAPTOP_COUNT = 2;

    private DeviceEntRepository repository;
    private final LaptopEnt laptop = new LaptopEnt("Samsung", "L20", 1266, 8);
    private final SmartphoneEnt smartphone = new SmartphoneEnt("Xiaomi", "Mi10", 400, 6.6);
    private final LaptopEnt laptop1 = new LaptopEnt("Apple", "Macbook", 866, 32);

    @BeforeEach
    void initialize() {
        repository = new DeviceEntRepository();
        assertTrue(repository.getAllDevices().isEmpty());
        repository.addDevice(laptop);
        repository.addDevice(smartphone);
        repository.addDevice(laptop1);
    }

    @Test
    void testAddDevice() {
        final int DEVICE_COUNT = repository.getAllDevices().size();
        SmartphoneEnt test = new SmartphoneEnt("Test", "Tested", 666, 6.66);
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
        SmartphoneEnt test = new SmartphoneEnt("Update", "Update", 666, 6.66);
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