package pl.tks.rent_service.rest.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.rest.model.DeviceDtoRest;
import pl.tks.rent_service.rest.model.EventDtoRest;

import java.util.UUID;


@Mapper(componentModel = "cdi")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventDtoRest eventToEventDto(Event event);

    Event toEvent(EventDtoRest eventDtoSoap);

    default String mapUuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID mapStringToUuid(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

    default Device mapDeviceDtoToDevice(DeviceDtoRest deviceDtoRest) {
        if (deviceDtoRest == null) {
            return null;
        }
        if (deviceDtoRest.getBatteryLifetime() != 0) {
            return DeviceMapper.INSTANCE.toSmartphone(deviceDtoRest);
        }
        if (deviceDtoRest.getMemoryInGb() != 0) {
            return DeviceMapper.INSTANCE.toLaptop(deviceDtoRest);
        }
        return null;
    }

    default DeviceDtoRest mapDeviceToDeviceDto(Device device) {
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
