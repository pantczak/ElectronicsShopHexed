package pl.tks.rent_service.soap.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.soap.model.DeviceDtoSoap;
import pl.tks.rent_service.soap.model.EventDtoSoap;

import java.util.UUID;


@Mapper(componentModel = "cdi")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDtoSoap eventToEventDto(Event event);

    Event toEvent(EventDtoSoap eventDtoSoap);

    default String mapUuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID mapStringToUuid(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default Device mapDeviceDtoToDevice(DeviceDtoSoap deviceDtoSoap) {
        if (deviceDtoSoap == null) {
            return null;
        }
        if (deviceDtoSoap.getBatteryLifetime() != 0) {
            return DeviceMapper.INSTANCE.toSmartphone(deviceDtoSoap);
        }
        if (deviceDtoSoap.getMemoryInGb() != 0) {
            return DeviceMapper.INSTANCE.toLaptop(deviceDtoSoap);
        }
        return null;
    }

    default DeviceDtoSoap mapDeviceToDeviceDto(Device device) {
        if (device == null) {
            return null;
        }
        if (device instanceof Smartphone) {
            return DeviceMapper.INSTANCE.smartphoneToDeviceDto((Smartphone) device);
        }
        if (device instanceof Laptop) {
            return DeviceMapper.INSTANCE.laptopToDeviceDto((Laptop) device);
        }
        return null;
    }


}
