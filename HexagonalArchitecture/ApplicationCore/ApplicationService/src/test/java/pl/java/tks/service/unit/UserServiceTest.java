package pl.java.tks.service.unit;

import pl.java.tks.infrastructure.UserPort;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_domain.model.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.java.tks.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock(name = "userPort")
    private UserPort userPort;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    private final Client testClient = new Client("Adam", "Adam", "asdsa", "asd", 14);
    private final Employee testEmployee = new Employee("Adam", "Adam", "asdsa", "asd");
    private final Administrator testAdministrator = new Administrator("Adam", "Adam", "asdsa", "asd");

    private final List<User> list = new ArrayList<>(List.of(testEmployee, testClient, testAdministrator));

    @Test
    void addClient() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());

        //when
        service.addClient(client);
        verify(userPort).addUser(userArgumentCaptor.capture());

        //then
        then(userPort).should().addUser(userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(client.getName(), userArgumentCaptor.getValue().getName());

    }

    @Test
    void addClientByParams() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());

        //when
        service.addClient(client.getLogin(), client.getName(), client.getLastName(), client.getPassword(), client.getAge());
        verify(userPort).addUser(userArgumentCaptor.capture());

        //then
        then(userPort).should().addUser(userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(client.getName(), userArgumentCaptor.getValue().getName());
    }

    @Test
    void addEmployee() {
        //given
        Employee employee = testEmployee;
        employee.setUuid(UUID.randomUUID());

        //when
        service.addEmployee(employee);
        verify(userPort).addUser(userArgumentCaptor.capture());

        //then
        then(userPort).should().addUser(userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(employee.getName(), userArgumentCaptor.getValue().getName());
    }

    @Test
    void testAddEmployee() {
        //given
        Employee employee = testEmployee;
        employee.setUuid(UUID.randomUUID());

        //when
        service.addEmployee(employee.getLogin(), employee.getName(), employee.getLastName(), employee.getPassword());
        verify(userPort).addUser(userArgumentCaptor.capture());

        //then
        then(userPort).should().addUser(userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(employee.getName(), userArgumentCaptor.getValue().getName());
    }

    @Test
    void addAdministrator() {
        //given
        Administrator administrator = testAdministrator;
        administrator.setUuid(UUID.randomUUID());

        //when
        service.addAdministrator(administrator);
        verify(userPort).addUser(userArgumentCaptor.capture());

        //then
        then(userPort).should().addUser(userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(administrator.getName(), userArgumentCaptor.getValue().getName());
    }

    @Test
    void addAdministratorByParams() {
        //given
        Administrator administrator = testAdministrator;
        administrator.setUuid(UUID.randomUUID());

        //when
        service.addAdministrator(administrator.getLogin(), administrator.getName(), administrator.getLastName(),
                administrator.getPassword());
        verify(userPort).addUser(userArgumentCaptor.capture());

        //then
        then(userPort).should().addUser(userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(administrator.getName(), userArgumentCaptor.getValue().getName());
    }

    @Test
    void getUser() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(userPort.getUser(any(UUID.class))).willReturn(client);
        //when
        User user = service.getUser(client.getUuid());
        //then
        then(userPort).should().getUser(client.getUuid());
        then(userPort).shouldHaveNoMoreInteractions();
        assertNotNull(user);
    }

    @Test
    void getUserByLogin() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(userPort.getUser(any(String.class))).willReturn(client);
        //when
        User user = service.getUser(client.getLogin());
        //then
        then(userPort).should().getUser(client.getLogin());
        then(userPort).shouldHaveNoMoreInteractions();
        assertNotNull(user);
    }

    @Test
    void getAllClients() {
        //given
        given(userPort.getAllClients()).willReturn(list.stream().filter(user -> user instanceof Client)
                .map(Client.class::cast).collect(Collectors.toList()));
        //when
        List<Client> list = service.getAllClients();
        //then
        then(userPort).should().getAllClients();
        then(userPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAllEmployees() {
        //given
        given(userPort.getAllEmployees()).willReturn(list.stream().filter(user -> user instanceof Employee)
                .collect(Collectors.toList()));
        //when
        List<User> list = service.getAllEmployees();
        //then
        then(userPort).should().getAllEmployees();
        then(userPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAllAdministrators() {
        //given
        given(userPort.getAllAdministrators()).willReturn(list.stream().filter(user -> user instanceof Administrator)
                .collect(Collectors.toList()));
        //when
        List<User> list = service.getAllAdministrators();
        //then
        then(userPort).should().getAllAdministrators();
        then(userPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAll() {
        //given
        given(userPort.getAll()).willReturn(list);
        //when
        List<User> list = service.getAll();
        //then
        then(userPort).should().getAll();
        then(userPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void updateUser() {
        //given
        Administrator administrator = testAdministrator;
        administrator.setUuid(UUID.randomUUID());
        String newName = "NewName";
//        given(userPort.getUser(any(UUID.class))).willReturn(administrator);
        given(userPort.getUser(any(String.class))).willReturn(administrator);
        //when
        service.updateUser(administrator, administrator.getLogin(), newName, administrator.getLastName());
        Mockito.verify(userPort).updateUser(uuidArgumentCaptor.capture(), userArgumentCaptor.capture());
        //then
        then(userPort).should().updateUser(uuidArgumentCaptor.getValue(), userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(newName, userArgumentCaptor.getValue().getName());
    }

    @Test
    void updateClient() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        String newName = "NewName";
      //  given(userPort.getUser(any(UUID.class))).willReturn(client);
        given(userPort.getUser(any(String.class))).willReturn(client);
        //when
        service.updateClient(client, client.getLogin(), newName, client.getLastName(), client.getAge());
        Mockito.verify(userPort).updateUser(uuidArgumentCaptor.capture(), userArgumentCaptor.capture());
        //then
        then(userPort).should().updateUser(uuidArgumentCaptor.getValue(), userArgumentCaptor.getValue());
        then(userPort).shouldHaveNoMoreInteractions();
        assertEquals(newName, userArgumentCaptor.getValue().getName());
    }

    @Test
    void activateUser() {
        //given
        Client client = testClient;
        client.setActive(false);
        //when
        service.activateUser(client);
        //then
        assertTrue(client.isActive());
    }

    @Test
    void deactivateUser() {
        //given
        Client client = testClient;
        client.setActive(true);
        //when
        service.deactivateUser(client);
        //then
        assertFalse(client.isActive());
    }

    @Test
    void getUserByLoginPassword() {
        //given

        //when

        //then
    }

    @Test
    void isUserActiveWhenFalse() {
        Client client = testClient;
        client.setActive(false);
        given(userPort.getUser(any(String.class))).willReturn(client);
        //when
        service.isUserActive(client.getLogin());
        //then
        then(userPort).should().getUser(client.getLogin());
        then(userPort).shouldHaveNoMoreInteractions();
        assertFalse(client.isActive());
    }

    @Test
    void isUserActiveWhenTrue() {
        Client client = testClient;
        client.setActive(true);
        given(userPort.getUser(any(String.class))).willReturn(client);
        //when
        service.isUserActive(client.getLogin());
        then(userPort).should().getUser(client.getLogin());
        then(userPort).shouldHaveNoMoreInteractions();
        //then
        assertTrue(client.isActive());
    }
}