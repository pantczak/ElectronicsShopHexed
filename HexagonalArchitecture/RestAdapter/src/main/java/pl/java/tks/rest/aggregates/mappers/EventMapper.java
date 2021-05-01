package pl.java.tks.rest.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.rest.model.DeviceDtoRest;
import pl.java.tks.rest.model.EventDtoRest;

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
