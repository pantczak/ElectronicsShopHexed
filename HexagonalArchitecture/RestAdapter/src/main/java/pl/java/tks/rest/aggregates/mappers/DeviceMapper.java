package pl.java.tks.rest.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.rest.model.DeviceDtoRest;


import java.util.UUID;

@Mapper(imports = UUID.class, componentModel = "cdi")
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceDtoRest smartphoneToDeviceDto(Smartphone smartphone);

    DeviceDtoRest laptopToDeviceDto(Laptop laptop);

    Smartphone toSmartphone(DeviceDtoRest deviceDtoSoap);

    Laptop toLaptop(DeviceDtoRest deviceDtoSoap);

    default String mapUuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID mapStringToUuid(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

}
