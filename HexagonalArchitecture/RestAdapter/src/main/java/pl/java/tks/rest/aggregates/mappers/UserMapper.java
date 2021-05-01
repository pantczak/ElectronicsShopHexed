package pl.java.tks.rest.aggregates.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.rest.model.UserDtoRest;


import java.util.UUID;

@Mapper(imports = UUID.class, componentModel = "cdi")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDtoRest clientToUserDto(Client client);

    UserDtoRest administratorToUserDto(Administrator administrator);

    UserDtoRest employeeToUserDto(Employee employee);

    Client toClient(UserDtoRest userDtoSoap);

    Administrator toAdministrator(UserDtoRest userDtoSoap);

    Employee toEmployee(UserDtoRest userDtoSoap);

    default String mapUuidToString(UUID value) {
        return value != null ? value.toString() : null;
    }

    default UUID mapStringToUuid(String value) {
        return value != null ? UUID.fromString(value) : null;
    }


}
