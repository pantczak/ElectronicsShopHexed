package pl.java.tks.soap.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.soap.model.DeviceDtoSoap;

import java.util.UUID;

@Mapper(imports = UUID.class, componentModel = "cdi")
public interface DeviceMapper {

    DeviceMapper INSTANCE = Mappers.getMapper(DeviceMapper.class);

    DeviceDtoSoap smartphoneToDeviceDto(Smartphone smartphone);

    DeviceDtoSoap laptopToDeviceDto(Laptop laptop);

    Smartphone toSmartphone(DeviceDtoSoap deviceDtoSoap);

    Laptop toLaptop(DeviceDtoSoap deviceDtoSoap);

    default String mapUuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID mapStringToUuid(String value) {
        return value != null ? UUID.fromString(value) : null;
    }

}
