package pl.java.tks.soap.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_domain.model.user.User;
import pl.java.tks.soap.model.UserDtoSoap;

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
