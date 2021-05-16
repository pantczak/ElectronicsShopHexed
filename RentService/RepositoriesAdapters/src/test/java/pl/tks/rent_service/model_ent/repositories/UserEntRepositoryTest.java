package pl.tks.rent_service.model_ent.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.tks.rent_service.model_ent.model.user.AdministratorEnt;
import pl.tks.rent_service.model_ent.model.user.ClientEnt;
import pl.tks.rent_service.model_ent.model.user.EmployeeEnt;

import static org.junit.jupiter.api.Assertions.*;

class UserEntRepositoryTest {

    private final static int REPOSITORY_SIZE = 4;
    private final static int CLIENT_COUNT = 2;
    private final static int EMPLOYEE_COUNT = 1;
    private final static int ADMIN_COUNT = 1;

    private UserEntRepository repository;
    private final ClientEnt client = new ClientEnt("Adam", "Kowalski", "adam123", "password");
    private final ClientEnt client1 = new ClientEnt("Jan", "Kowalski", "jan123", "password");
    private final EmployeeEnt employee = new EmployeeEnt("Pan", "Paweł", "pawel_jumper", "toboli");
    private final AdministratorEnt administrator = new AdministratorEnt("Doktor", "Nauk", "JanRodzyn", "rodzyn_padl");

    @BeforeEach
    void initialize() {
        repository = new UserEntRepository();
        assertTrue(repository.getAll().isEmpty());
        repository.addUser(client);
        repository.addUser(client1);
        repository.addUser(employee);
        repository.addUser(administrator);
    }

    @Test
    void addUser() {
        ClientEnt addTest = new ClientEnt("Test", "Add", "add123", "password");
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
        ClientEnt update = new ClientEnt("Update", "Add", "Update123", "password");
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