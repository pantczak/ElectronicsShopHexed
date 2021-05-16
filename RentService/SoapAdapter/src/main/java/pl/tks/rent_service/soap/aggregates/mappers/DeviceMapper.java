package pl.tks.rent_service.soap.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.soap.model.DeviceDtoSoap;

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
