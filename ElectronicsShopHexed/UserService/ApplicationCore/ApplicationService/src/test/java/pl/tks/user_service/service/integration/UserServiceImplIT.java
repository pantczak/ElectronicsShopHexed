package pl.tks.user_service.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.tks.user_service.aggregates.UserRepositoryAdapter;
import pl.tks.user_service.model_ent.repositories.UserEntRepository;
import pl.tks.user_service.model_domain.model.user.Administrator;
import pl.tks.user_service.model_domain.model.user.Client;
import pl.tks.user_service.model_domain.model.user.Employee;
import pl.tks.user_service.model_domain.model.user.User;
import pl.tks.user_service.service.UserServiceImpl;
import pl.tks.user_service.service.interfaces.UserService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplIT {

    private UserService service;

    private final Client testClient = new Client("Adam", "Adam", "asdsa", "asd", 14);
    private final Employee testEmployee = new Employee("Agfgdfdam", "Addfgam", "asdfgdsa", "asd");
    private final Administrator testAdministrator = new Administrator("Adam", "Adam", "adfgdfgsdsa", "asd");

    @BeforeEach
    public void init() {
        testClient.setUuid(UUID.randomUUID());
        testEmployee.setUuid(UUID.randomUUID());
        testEmployee.setUuid(UUID.randomUUID());

        service = new UserServiceImpl(new UserRepositoryAdapter(new UserEntRepository()));

        service.addClient(testClient);
        service.addAdministrator(testAdministrator);
        service.addEmployee(testEmployee);
    }

    @Test
    void addClient() {
        Client newClient = new Client("Adam", "Adam", "new", "asd", 14);
        int size = service.getAll().size();
        service.addClient(newClient);
        assertEquals(size + 1, service.getAll().size());
    }

    @Test
    void testAddClient() {
        Client newClient = new Client("Adam", "Adam", "new", "asd", 14);
        int size = service.getAll().size();
        service.addClient(newClient.getLogin(), newClient.getName(), newClient.getLastName(), newClient.getPassword(), newClient.getAge());
        assertEquals(size + 1, service.getAll().size());
    }

    @Test
    void addEmployee() {
        Employee newEmployee = new Employee("Adam", "Adam", "newEmp", "asd");
        int size = service.getAll().size();
        service.addEmployee(newEmployee);
        assertEquals(size + 1, service.getAll().size());
    }

    @Test
    void testAddEmployee() {
        Employee newEmployee = new Employee("Adam", "Adam", "newEmp", "asd");
        int size = service.getAll().size();
        service.addEmployee(newEmployee.getLogin(), newEmployee.getName(), newEmployee.getLastName(), newEmployee.getPassword());
        assertEquals(size + 1, service.getAll().size());
    }

    @Test
    void addAdministrator() {
        Administrator administrator = new Administrator("Adam", "Adam", "newEmp", "asd");
        int size = service.getAll().size();
        service.addAdministrator(administrator);
        assertEquals(size + 1, service.getAll().size());
    }

    @Test
    void testAddAdministrator() {
        Administrator administrator = new Administrator("Adam", "Adam", "newEmp", "asd");
        int size = service.getAll().size();
        service.addAdministrator(administrator.getLogin(), administrator.getName(), administrator.getLastName(), administrator.getPassword());
        assertEquals(size + 1, service.getAll().size());
    }

    @Test
    void getUser() {
        assertEquals(testClient.getLogin(), service.getUser(testClient.getLogin()).getLogin());
    }

    @Test
    void testGetUser() {
        assertEquals(testClient.getLogin(), service.getUser(service.getAll().get(0).getUuid()).getLogin());
    }

    @Test
    void getAllClients() {
        assertEquals(1, service.getAllClients().size());
    }

    @Test
    void getAllEmployees() {
        assertEquals(1, service.getAllEmployees().size());
    }

    @Test
    void getAllAdministrators() {
        assertEquals(1, service.getAllAdministrators().size());
    }

    @Test
    void getAll() {
        assertEquals(3, service.getAll().size());
    }

    @Test
    void updateUser() {
        Administrator update = new Administrator("update", "update", "update", "asd");
        User user = service.getAll().get(2);
        service.updateUser(user, update.getLogin(), update.getName(), update.getLastName());
        assertEquals(update.getName(), service.getAll().get(2).getName());

    }

    @Test
    void updateClient() {
        Client update = new Client("update", "update", "update", "asd", 14);
        User user = service.getAll().get(0);
        service.updateClient(user, update.getLogin(), update.getName(), update.getLastName(), update.getAge());
        assertEquals(update.getName(), service.getAll().get(0).getName());
    }

    @Test
    void activateUser() {
        service.activateUser(service.getAll().get(0));
        assertTrue(service.getAll().get(0).isActive());
    }

    @Test
    void deactivateUser() {
        service.deactivateUser(service.getAll().get(0));
        assertFalse(service.getAll().get(0).isActive());
    }

    @Test
    void getUserByLoginPassword() {
        assertEquals(testClient.getLogin(), service.getUserByLoginPassword(testClient.getLogin(), testClient.getPassword()).getLogin());
    }

    @Test
    void isUserActive() {
        assertTrue(service.isUserActive(testClient.getLogin()));
    }
}