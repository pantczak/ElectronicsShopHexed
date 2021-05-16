package aggregates.integration;

import org.junit.jupiter.api.*;
import pl.tks.rent_service.aggregates.UserRepositoryAdapter;

import pl.tks.rent_service.model_ent.model.user.AdministratorEnt;
import pl.tks.rent_service.model_ent.model.user.ClientEnt;
import pl.tks.rent_service.model_ent.model.user.EmployeeEnt;
import pl.tks.rent_service.model_ent.repositories.UserEntRepository;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.User;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryAdapterIT {

    private UserRepositoryAdapter adapter;
    private UserEntRepository repository;
    //test data
    private final Client testClient = new Client("TestName", "TestSurname", "TsTLgn", "TestPasswoRD");
    //initial data
    private static final int INITIAL_REPOSITORY_SIZE = 3;
    private final ClientEnt initialClient = new ClientEnt("Adam", "Ktoto", "lgon123", "passwd123");
    private final EmployeeEnt initialEmployee = new EmployeeEnt("Employ", "Me", "Pls", "IAmGood");
    private final AdministratorEnt initialAdministrator = new AdministratorEnt("Good", "Person", "Team", "Player");

    @BeforeAll
    void initRepository() {
        initialClient.setUuid(UUID.randomUUID());
        initialEmployee.setUuid(UUID.randomUUID());
        initialAdministrator.setUuid(UUID.randomUUID());
    }

    @BeforeEach
    void init() {
        repository = new UserEntRepository();
        repository.addUser(initialAdministrator);
        repository.addUser(initialClient);
        repository.addUser(initialEmployee);
        //verify precondition
        assertEquals(INITIAL_REPOSITORY_SIZE, repository.getAll().size());
        adapter = new UserRepositoryAdapter(repository);
    }

    @Test
    @Order(1)
    void addUser() {
        adapter.addUser(testClient);
        assertEquals(INITIAL_REPOSITORY_SIZE + 1, repository.getAll().size());
    }

    @Test
    @Order(2)
    void getUserByUUID() {
        Client client = (Client) adapter.getUser(initialClient.getUuid());
        assertNotNull(client);
        assertEquals(initialClient.getUuid(), client.getUuid());
        assertEquals(initialClient.getLastName(), client.getLastName());
        assertEquals(initialClient.getPassword(), client.getPassword());
        assertEquals(initialClient.getLogin(), client.getLogin());
        assertEquals(initialClient.getName(), client.getName());
    }

    @Test
    @Order(3)
    void getUserByLogin() {
        Client client = (Client) adapter.getUser(initialClient.getLogin());
        assertNotNull(client);
        assertEquals(initialClient.getUuid(), client.getUuid());
        assertEquals(initialClient.getLastName(), client.getLastName());
        assertEquals(initialClient.getPassword(), client.getPassword());
        assertEquals(initialClient.getLogin(), client.getLogin());
        assertEquals(initialClient.getName(), client.getName());
    }

    @Test
    @Order(4)
    void getAllClients() {
        List<Client> list = adapter.getAllClients();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    @Order(5)
    void getAllEmployees() {
        List<User> list = adapter.getAllEmployees();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    @Order(6)
    void getAllAdministrators() {
        List<User> list = adapter.getAllAdministrators();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    @Order(7)
    void getAll() {
        List<User> list = adapter.getAll();
        assertFalse(list.isEmpty());
        assertEquals(INITIAL_REPOSITORY_SIZE, list.size());
    }

    @Test
    @Order(8)
    void updateUser() {
        adapter.updateUser(initialClient.getUuid(), testClient);
        assertEquals(testClient.getName(), repository.getUser(initialClient.getUuid()).getName());
        assertEquals(testClient.getLastName(), repository.getUser(initialClient.getUuid()).getLastName());
    }
}