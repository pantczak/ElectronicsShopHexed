package pl.tks.rent_service.soap.aggregates.mappers;

import org.junit.jupiter.api.Test;
import pl.tks.rent_service.model_domain.model.user.Administrator;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.Employee;
import pl.tks.rent_service.soap.model.UserDtoSoap;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {


    @Test
    void clientToUserDto() {
        //given
        Client client = new Client("Adam", "Adamczyk", "adam123", "password");
        client.setUuid(UUID.randomUUID());
        //when
        UserDtoSoap userDtoSoap = UserMapper.INSTANCE.clientToUserDto(client);

        //then
        assertNotNull(userDtoSoap);
        assertEquals(client.getUuid().toString(), userDtoSoap.getUuid());
        assertEquals("Adam", userDtoSoap.getName());
        assertEquals("Adamczyk", userDtoSoap.getLastName());
        assertEquals("adam123", userDtoSoap.getLogin());
        assertEquals("password", userDtoSoap.getPassword());

    }

    @Test
    void administratorToUserDto() {
        //given
        Administrator administrator = new Administrator("Adam", "Adamczyk", "adam123", "password");
        administrator.setUuid(UUID.randomUUID());
        //when
        UserDtoSoap userDtoSoap = UserMapper.INSTANCE.administratorToUserDto(administrator);

        //then
        assertNotNull(userDtoSoap);
        assertEquals(administrator.getUuid().toString(), userDtoSoap.getUuid());
        assertEquals("Adam", userDtoSoap.getName());
        assertEquals("Adamczyk", userDtoSoap.getLastName());
        assertEquals("adam123", userDtoSoap.getLogin());
        assertEquals("password", userDtoSoap.getPassword());

    }

    @Test
    void employeeToUserDto() {
        //given
        Employee employee = new Employee("Adam", "Adamczyk", "adam123", "password");
        employee.setUuid(UUID.randomUUID());
        //when
        UserDtoSoap userDtoSoap = UserMapper.INSTANCE.employeeToUserDto(employee);

        //then
        assertNotNull(userDtoSoap);
        assertEquals(employee.getUuid().toString(), userDtoSoap.getUuid());
        assertEquals("Adam", userDtoSoap.getName());
        assertEquals("Adamczyk", userDtoSoap.getLastName());
        assertEquals("adam123", userDtoSoap.getLogin());
        assertEquals("password", userDtoSoap.getPassword());
    }

    @Test
    void toClient() {
        //given
        Client client = new Client("Adam", "Adamczyk", "adam123", "password");
        client.setUuid(UUID.randomUUID());
        UserDtoSoap userDtoSoap = UserMapper.INSTANCE.clientToUserDto(client);
        //when
        Client convertedFromDto = UserMapper.INSTANCE.toClient(userDtoSoap);
        //then
        assertEquals(client, convertedFromDto);


    }

    @Test
    void toAdministrator() {
        //given
        Administrator administrator = new Administrator("Adam", "Adamczyk", "adam123", "password");
        administrator.setUuid(UUID.randomUUID());
        UserDtoSoap userDtoSoap = UserMapper.INSTANCE.administratorToUserDto(administrator);
        //when
        Administrator convertedFromDto = UserMapper.INSTANCE.toAdministrator(userDtoSoap);
        //then
        assertEquals(administrator, convertedFromDto);
    }

    @Test
    void toEmployee() {
        //given
        Employee employee = new Employee("Adam", "Adamczyk", "adam123", "password");
        employee.setUuid(UUID.randomUUID());
        UserDtoSoap userDtoSoap = UserMapper.INSTANCE.employeeToUserDto(employee);
        //when
        Employee convertedFromDto = UserMapper.INSTANCE.toEmployee(userDtoSoap);
        //then
        assertEquals(employee, convertedFromDto);
    }
}