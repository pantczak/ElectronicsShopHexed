package pl.java.tks.service.unit;

import pl.java.tks.infrastructure.DevicePort;
import pl.java.tks.infrastructure.EventPort;
import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.java.tks.service.DeviceService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @InjectMocks
    private DeviceService service;

    @Mock(name = "devicePort")
    private DevicePort devicePort;

    @Mock(name = "eventPort")
    private EventPort eventPort;

    @Captor
    private ArgumentCaptor<Device> deviceArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    private final Laptop testLaptop = new Laptop("Macbook", "Pro", 1499, 32);
    private final Smartphone testSmartphone = new Smartphone("Xiaomi", "Mi10", 450, 9.9);
    private final List<Device> testDeviceList = new ArrayList<>(List.of(testSmartphone, testLaptop));

    @Test
    void getDevice() {
        //given
        Laptop laptop = testLaptop;
        laptop.setUuid(UUID.randomUUID());
        given(devicePort.getDevice(any(UUID.class))).willReturn(testLaptop);

        //when
        Device device = service.getDevice(laptop.getUuid());

        //then
        then(devicePort).should().getDevice(laptop.getUuid());
        then(devicePort).shouldHaveNoMoreInteractions();
        assertNotNull(device);

    }

    @Test
    void deleteDevice() {
        //given
        Laptop laptop = testLaptop;
        laptop.setUuid(UUID.randomUUID());
        laptop.setAvailable(true);
        given(devicePort.getDevice(any(UUID.class))).willReturn(laptop);
        given(eventPort.getAllEvents()).willReturn(new ArrayList<>());
        //when
        service.deleteDevice(laptop.getUuid());
        //then
        verify(devicePort, times(2)).getDevice(laptop.getUuid());
        then(devicePort).should().deleteDevice(laptop.getUuid());
        then(devicePort).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAllLaptops() {
        //given
        given(devicePort.getAllLaptops()).willReturn(testDeviceList.stream().filter(device -> device instanceof Laptop)
                .map(Laptop.class::cast).collect(Collectors.toList()));
        //when
        List<Laptop> list = service.getAllLaptops();
        //then
        then(devicePort).should().getAllLaptops();
        then(devicePort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());

    }

    @Test
    void getAllSmartphones() {
        //given
        given(devicePort.getAllSmartphones()).willReturn(testDeviceList.stream().filter(device -> device instanceof Smartphone)
                .map(Smartphone.class::cast).collect(Collectors.toList()));
        //when
        List<Smartphone> list = service.getAllSmartphones();
        //then
        then(devicePort).should().getAllSmartphones();
        then(devicePort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void addLaptopByRef() {
        //given
        Laptop laptop = testLaptop;
        laptop.setUuid(UUID.randomUUID());
        //when
        service.addLaptop(laptop);
        verify(devicePort).addDevice(deviceArgumentCaptor.capture());
        //then
        then(devicePort).should().addDevice(deviceArgumentCaptor.getValue());
    }

    @Test
    void addLaptopByParams() {
        //given
        Laptop laptop = testLaptop;
        laptop.setUuid(UUID.randomUUID());
        //when
        service.addLaptop(laptop.getBrand(), laptop.getModel(), laptop.getWeightInGrams(), laptop.getMemoryInGb());
        verify(devicePort).addDevice(deviceArgumentCaptor.capture());
        //then
        then(devicePort).should().addDevice(deviceArgumentCaptor.getValue());
    }

    @Test
    void addSmartphoneByRef() {
        //given
        Smartphone smartphone = testSmartphone;
        smartphone.setUuid(UUID.randomUUID());
        //when
        service.addSmartphone(smartphone);
        verify(devicePort).addDevice(deviceArgumentCaptor.capture());
        //then
        then(devicePort).should().addDevice(deviceArgumentCaptor.getValue());
    }

    @Test
    void addSmartphoneByParams() {
        //given
        Smartphone smartphone = testSmartphone;
        smartphone.setUuid(UUID.randomUUID());
        //when
        service.addSmartphone(smartphone.getBrand(), smartphone.getModel(), smartphone.getWeightInGrams(), smartphone.getWeightInGrams());
        verify(devicePort).addDevice(deviceArgumentCaptor.capture());
        //then
        then(devicePort).should().addDevice(deviceArgumentCaptor.getValue());
    }

    @Test
    void updateLaptop() {
        //given
        Laptop laptop = testLaptop;
        laptop.setUuid(UUID.randomUUID());
        laptop.setAvailable(true);
        String brand = "NewBrand";
        Device device = laptop;
        testDeviceList.add(laptop);
        given(devicePort.getAllDevices()).willReturn(List.of(device));
        //when
        service.updateLaptop(laptop, brand, "NewModel", 1234, 8);
        verify(devicePort).updateDevice(uuidArgumentCaptor.capture(), deviceArgumentCaptor.capture());
        //then
        then(devicePort).should().getAllDevices();
        then(devicePort).should().updateDevice(uuidArgumentCaptor.getValue(), deviceArgumentCaptor.getValue());
        then(devicePort).shouldHaveNoMoreInteractions();
        assertEquals(laptop.getUuid(), uuidArgumentCaptor.getValue());
        assertEquals(brand, deviceArgumentCaptor.getValue().getBrand());
    }

    @Test
    void updateSmartphone() {
        //given
        Smartphone smartphone = testSmartphone;
        smartphone.setUuid(UUID.randomUUID());
        smartphone.setAvailable(true);
        String brand = "NewBrand";
        testDeviceList.add(smartphone);
        given(devicePort.getAllDevices()).willReturn(testDeviceList);
        //when
        service.updateSmartphone(smartphone, brand, "NewModel", 1234, 8);
        verify(devicePort).updateDevice(uuidArgumentCaptor.capture(), deviceArgumentCaptor.capture());
        //then
        then(devicePort).should().getAllDevices();
        then(devicePort).should().updateDevice(uuidArgumentCaptor.getValue(), deviceArgumentCaptor.getValue());
        then(devicePort).shouldHaveNoMoreInteractions();
        assertEquals(smartphone.getUuid(), uuidArgumentCaptor.getValue());
        assertEquals(brand, deviceArgumentCaptor.getValue().getBrand());
    }

    @Test
    void getAllDevices() {
        //given
        given(devicePort.getAllDevices()).willReturn(testDeviceList);
        //when
        List<Device> list = service.getAllDevices();
        //then
        then(devicePort).should().getAllDevices();
        then(devicePort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }
}