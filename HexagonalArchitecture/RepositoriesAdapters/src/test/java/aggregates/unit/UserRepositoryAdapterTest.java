package aggregates.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.java.tks.aggregates.UserRepositoryAdapter;
import pl.java.tks.aggregates.converters.UserConverter;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_domain.model.user.User;
import pl.java.tks.model_ent.model.user.AdministratorEnt;
import pl.java.tks.model_ent.model.user.ClientEnt;
import pl.java.tks.model_ent.model.user.EmployeeEnt;
import pl.java.tks.model_ent.model.user.UserEnt;
import pl.java.tks.model_ent.repositories.UserEntRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @InjectMocks
    private UserRepositoryAdapter adapter;

    @Mock(name = "repository")
    private UserEntRepository repository;

    @Captor
    private ArgumentCaptor<UserEnt> userEntArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    private final Client testClient = new Client("Adam", "Adam", "asdsa", "asd", 14);
    private final Employee testEmployee = new Employee("Adam", "Adam", "asdsa", "asd");
    private final Administrator testAdministrator = new Administrator("Adam", "Adam", "asdsa", "asd");

    private final List<User> list = new ArrayList<>(List.of(testEmployee, testClient, testAdministrator));


    @Test
    void addUser() {
        //given testClient

        //when
        adapter.addUser(testClient);
        Mockito.verify(repository).addUser(userEntArgumentCaptor.capture());

        //then
        then(repository).should().addUser(userEntArgumentCaptor.getValue());
        then(repository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getUserByUUID() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(repository.getUser(any(UUID.class))).willReturn(UserConverter.convertUserToEnt(client));
        //when
        User user = adapter.getUser(client.getUuid());
        //then
        then(repository).should().getUser(client.getUuid());
        then(repository).shouldHaveNoMoreInteractions();
        assertNotNull(user);
    }

    @Test
    void getUserByLogin() {
        //given testClient

        given(repository.getUser(any(String.class))).willReturn(UserConverter.convertUserToEnt(testClient));
        //when
        User user = adapter.getUser(testClient.getLogin());
        //then
        then(repository).should().getUser(testClient.getLogin());
        then(repository).shouldHaveNoMoreInteractions();
        assertNotNull(user);
    }

    @Test
    void getAllClients() {
        //given
        given(repository.getAllClients()).willReturn(list.stream().filter(user -> user instanceof Client).map(UserConverter::convertUserToEnt)
                .map(ClientEnt.class::cast).collect(Collectors.toList()));
        //when
        List<Client> list = adapter.getAllClients();
        //then
        then(repository).should().getAllClients();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAllEmployees() {
        //given
        given(repository.getAllEmployees()).willReturn(list.stream().filter(user -> user instanceof Employee).map(UserConverter::convertUserToEnt)
                .map(EmployeeEnt.class::cast).collect(Collectors.toList()));
        //when
        List<User> list = adapter.getAllEmployees();
        //then
        then(repository).should().getAllEmployees();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAllAdministrators() {
        //given
        given(repository.getAllAdministrators()).willReturn(list.stream().filter(user -> user instanceof Administrator).map(UserConverter::convertUserToEnt)
                .map(AdministratorEnt.class::cast).collect(Collectors.toList()));
        //when
        List<User> list = adapter.getAllAdministrators();
        //then
        then(repository).should().getAllAdministrators();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAll() {
        //given
        given(repository.getAll()).willReturn(list.stream().map(UserConverter::convertUserToEnt)
                .collect(Collectors.toList()));
        //when
        List<User> list = adapter.getAll();
        //then
        then(repository).should().getAll();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void updateUser() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        //when
        adapter.updateUser(client.getUuid(), client);
        Mockito.verify(repository).updateUser(uuidArgumentCaptor.capture(), userEntArgumentCaptor.capture());
        //then
        then(repository).should().updateUser(uuidArgumentCaptor.getValue(), userEntArgumentCaptor.getValue());
        then(repository).shouldHaveNoMoreInteractions();
    }
}