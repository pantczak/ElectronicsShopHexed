package pl.tks.user_service.model_domain.repositories;

import pl.tks.user_service.model_domain.model.user.Administrator;
import pl.tks.user_service.model_domain.model.user.Client;
import pl.tks.user_service.model_domain.model.user.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private final static int REPOSITORY_SIZE = 4;
    private final static int CLIENT_COUNT = 2;
    private final static int EMPLOYEE_COUNT = 1;
    private final static int ADMIN_COUNT = 1;

    private UserRepository repository;
    private final Client client = new Client("Adam", "Kowalski", "adam123", "password", 14);
    private final Client client1 = new Client("Jan", "Kowalski", "jan123", "password", 18);
    private final Employee employee = new Employee("Pan", "Paweł", "pawel_jumper", "toboli");
    private final Administrator administrator = new Administrator("Doktor", "Nauk", "JanRodzyn", "rodzyn_padl");

    @BeforeEach
    void initialize() {
        repository = new UserRepository();
        assertTrue(repository.getAll().isEmpty());
        repository.addUser(client);
        repository.addUser(client1);
        repository.addUser(employee);
        repository.addUser(administrator);
    }

    @Test
    void addUser() {
        Client addTest = new Client("Test", "Add", "add123", "password", 99);
        repository.addUser(addTest);
        assertEquals(REPOSITORY_SIZE + 1, repository.getAll().size());
    }

    @Test
    void getUserByUUID() {
        assertEquals(client, repository.getUser(client.getUuid()));
    }

    @Test
    void GetUserByLogin() {
        assertEquals(client, repository.getUser(client.getLogin()));
    }

    @Test
    void updateUser() {
        Client update = new Client("Update", "Add", "Update123", "password", 11);
        repository.updateUser(client.getUuid(), update);
        assertEquals(client.getUuid(), update.getUuid());
    }

    @Test
    void getAllClients() {
        assertEquals(CLIENT_COUNT, repository.getAllClients().size());
    }

    @Test
    void getAllEmployees() {
        assertEquals(EMPLOYEE_COUNT, repository.getAllEmployees().size());
    }

    @Test
    void getAllAdministrators() {
        assertEquals(ADMIN_COUNT, repository.getAllAdministrators().size());
    }

    @Test
    void getAll() {
        assertEquals(REPOSITORY_SIZE, repository.getAll().size());
    }
}