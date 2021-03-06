package pl.tks.rent_service.soap.aggregates.mappers;

import org.junit.jupiter.api.Test;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.soap.model.DeviceDtoSoap;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeviceMapperTest {

    @Test
    void smartphoneToDeviceDto() {
        //given
        Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 1235, 21.3);
        smartphone.setUuid(UUID.randomUUID());
        //when
        DeviceDtoSoap smartphoneDto = DeviceMapper.INSTANCE.smartphoneToDeviceDto(smartphone);

        //then
        assertNotNull(smartphoneDto);
        assertEquals(smartphone.getUuid().toString(), smartphoneDto.getUuid());
        assertEquals(smartphone.getBrand(), smartphoneDto.getBrand());
        assertEquals(smartphone.getModel(), smartphoneDto.getModel());
        assertEquals(smartphone.getWeightInGrams(), smartphoneDto.getWeightInGrams());
        assertEquals(smartphone.getBatteryLifetime(), smartphoneDto.getBatteryLifetime());
    }

    @Test
    void laptopToDeviceDto() {
        //given
        Laptop laptop = new Laptop("Xiaomi", "Mi10", 1235, 21);
        laptop.setUuid(UUID.randomUUID());
        //when
        DeviceDtoSoap laptopDto = DeviceMapper.INSTANCE.laptopToDeviceDto(laptop);

        //then
        assertNotNull(laptop);
        assertEquals(laptop.getUuid().toString(), laptopDto.getUuid());
        assertEquals(laptop.getBrand(), laptopDto.getBrand());
        assertEquals(laptop.getModel(), laptopDto.getModel());
        assertEquals(laptop.getWeightInGrams(), laptopDto.getWeightInGrams());
        assertEquals(laptop.getMemoryInGb(), laptopDto.getMemoryInGb());
    }

    @Test
    void toSmartphone() {
        //given
        Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 1235, 21);
        smartphone.setUuid(UUID.randomUUID());
        DeviceDtoSoap smartphoneDto = DeviceMapper.INSTANCE.smartphoneToDeviceDto(smartphone);
        //when
        Smartphone convertedFromDto = DeviceMapper.INSTANCE.toSmartphone(smartphoneDto);
        //then
        assertEquals(smartphone, convertedFromDto);

    }

    @Test
    void toLaptop() {
        //given
        Laptop laptop = new Laptop("Xiaomi", "Mi10", 1235, 21);
        laptop.setUuid(UUID.randomUUID());
        DeviceDtoSoap laptopDto = DeviceMapper.INSTANCE.laptopToDeviceDto(laptop);
        //when
        Laptop convertedFromDto = DeviceMapper.INSTANCE.toLaptop(laptopDto);
        //then
        assertEquals(laptop, convertedFromDto);
    }
}