package pl.java.tks.rest.aggregates.mappers;

import org.junit.jupiter.api.Test;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.rest.model.UserDtoRest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest {

    @Test
    void clientToUserDto() {
        //given
        Client client = new Client("Adam", "Adamczyk", "adam123", "password", 19);
        client.setUuid(UUID.randomUUID());
        //when
        UserDtoRest userDtoSoap = UserMapper.INSTANCE.clientToUserDto(client);

        //then
        assertNotNull(userDtoSoap);
        assertEquals(client.getUuid().toString(), userDtoSoap.getUuid());
        assertEquals("Adam", userDtoSoap.getName());
        assertEquals("Adamczyk", userDtoSoap.getLastName());
        assertEquals("adam123", userDtoSoap.getLogin());
        assertEquals("password", userDtoSoap.getPassword());
        assertEquals(19, userDtoSoap.getAge());

    }

    @Test
    void administratorToUserDto() {
        //given
        Administrator administrator = new Administrator("Adam", "Adamczyk", "adam123", "password");
        administrator.setUuid(UUID.randomUUID());
        //when
        UserDtoRest userDtoSoap = UserMapper.INSTANCE.administratorToUserDto(administrator);

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
        UserDtoRest userDtoSoap = UserMapper.INSTANCE.employeeToUserDto(employee);

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
        Client client = new Client("Adam", "Adamczyk", "adam123", "password", 19);
        client.setUuid(UUID.randomUUID());
        UserDtoRest userDtoSoap = UserMapper.INSTANCE.clientToUserDto(client);
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
        UserDtoRest userDtoSoap = UserMapper.INSTANCE.administratorToUserDto(administrator);
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
        UserDtoRest userDtoSoap = UserMapper.INSTANCE.employeeToUserDto(employee);
        //when
        Employee convertedFromDto = UserMapper.INSTANCE.toEmployee(userDtoSoap);
        //then
        assertEquals(employee, convertedFromDto);
    }
}