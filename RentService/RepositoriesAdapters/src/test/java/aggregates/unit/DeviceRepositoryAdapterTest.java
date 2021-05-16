package aggregates.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tks.rent_service.aggregates.DeviceRepositoryAdapter;
import pl.tks.rent_service.aggregates.converters.DeviceConverter;

import pl.tks.rent_service.model_ent.model.resource.DeviceEnt;
import pl.tks.rent_service.model_ent.model.resource.LaptopEnt;
import pl.tks.rent_service.model_ent.model.resource.SmartphoneEnt;
import pl.tks.rent_service.model_ent.repositories.DeviceEntRepository;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeviceRepositoryAdapterTest {

    @InjectMocks
    private DeviceRepositoryAdapter adapter;

    @Mock(name = "repository")
    private DeviceEntRepository repository;

    @Captor
    private ArgumentCaptor<DeviceEnt> deviceEntArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    private final Laptop testDevice = new Laptop("Macbook", "Pro", 1234, 32);
    private final Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 600, 5.5);
    private final List<Device> deviceList = new ArrayList<>(List.of(testDevice, smartphone));

    @Test
    void addDevice() {
        //given testDevice

        //when
        adapter.addDevice(testDevice);
        Mockito.verify(repository).addDevice(deviceEntArgumentCaptor.capture());
        //then
        then(repository).should().addDevice(deviceEntArgumentCaptor.getValue());
        then(repository).shouldHaveNoMoreInteractions();
        assertEquals(testDevice.getModel(), deviceEntArgumentCaptor.getValue().getModel());
    }

    @Test
    void deleteDevice() {
        //given
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        //when
        adapter.deleteDevice(laptop.getUuid());
        //then
        then(repository).should().deleteDevice(laptop.getUuid());
        then(repository).shouldHaveNoMoreInteractions();

    }

    @Test
    void getDevice() {
        //given
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        given(repository.getDevice(laptop.getUuid())).willReturn(DeviceConverter.convertDeviceToEnt(laptop));
        //when
        Device device = adapter.getDevice(laptop.getUuid());
        //then
        then(repository).should().getDevice(laptop.getUuid());
        then(repository).shouldHaveNoMoreInteractions();
        assertNotNull(device);
    }

    @Test
    void getAllDevices() {
        //given
        given(repository.getAllDevices()).willReturn(deviceList.stream().map(DeviceConverter::convertDeviceToEnt)
                .collect(Collectors.toList()));
        //when
        List<Device> list = adapter.getAllDevices();
        //then
        then(repository).should().getAllDevices();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());

    }

    @Test
    void getAllLaptops() {
        //given
        given(repository.getAllLaptops()).willReturn(deviceList.stream().filter(device -> device instanceof Laptop)
                .map(DeviceConverter::convertDeviceToEnt).map(LaptopEnt.class::cast).collect(Collectors.toList()));
        //when
        List<Laptop> list = adapter.getAllLaptops();
        //then
        then(repository).should().getAllLaptops();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAllSmartphones() {
        //given
        given(repository.getAllSmartphones()).willReturn(deviceList.stream().filter(device -> device instanceof Smartphone)
                .map(DeviceConverter::convertDeviceToEnt).map(SmartphoneEnt.class::cast).collect(Collectors.toList()));
        //when
        List<Smartphone> list = adapter.getAllSmartphones();
        //then
        then(repository).should().getAllSmartphones();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void updateDevice() {
        //given
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        laptop.setAvailable(true);
        //when
        adapter.updateDevice(laptop.getUuid(), laptop);
        Mockito.verify(repository).updateDevice(uuidArgumentCaptor.capture(), deviceEntArgumentCaptor.capture());
        //then
        then(repository).should().updateDevice(uuidArgumentCaptor.getValue(), deviceEntArgumentCaptor.getValue());
        then(repository).shouldHaveNoMoreInteractions();
        assertEquals(laptop.getUuid(), uuidArgumentCaptor.getValue());
        assertEquals(laptop.getModel(), deviceEntArgumentCaptor.getValue().getModel());

    }
}