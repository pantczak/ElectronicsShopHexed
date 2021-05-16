package pl.tks.rent_service.soap.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import pl.tks.rent_service.model_domain.model.user.Administrator;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.Employee;
import pl.tks.rent_service.soap.model.UserDtoSoap;

import java.util.UUID;

@Mapper(imports = UUID.class,componentModel = "cdi")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDtoSoap clientToUserDto(Client client);

    UserDtoSoap administratorToUserDto(Administrator administrator);

    UserDtoSoap employeeToUserDto(Employee employee);

    Client toClient(UserDtoSoap userDtoSoap);

    Administrator toAdministrator(UserDtoSoap userDtoSoap);

    Employee toEmployee(UserDtoSoap userDtoSoap);

    default String mapUuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID mapStringToUuid(String value) {
        return value != null ? UUID.fromString(value) : null;
    }


}
