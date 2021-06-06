package pl.tks.user_service.service.interfaces;



import pl.tks.user_service.model_domain.model.user.Administrator;
import pl.tks.user_service.model_domain.model.user.Client;
import pl.tks.user_service.model_domain.model.user.Employee;
import pl.tks.user_service.model_domain.model.user.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface UserService extends Serializable {
    boolean addClient(Client client);

    boolean addClient(String login, String name, String lastName, String password, int age);

    boolean addEmployee(Employee employee);

    boolean addEmployee(String login, String name, String lastName, String password);

    boolean addAdministrator(Administrator administrator);

    boolean addAdministrator(String login, String name, String lastName, String password);

    User getUser(UUID uuid);

    User getUser(String login);

    List<Client> getAllClients();

    List<User> getAllEmployees();

    List<User> getAllAdministrators();

    List<User> getAll();

    boolean updateUser(User old, String login, String name, String lastName);

    boolean updateClient(User old, String login, String name, String lastName, int age);

    boolean activateUser(User user);

    boolean deactivateUser(User user);

    User getUserByLoginPassword(String login, String passwordAsString);

    boolean isUserActive(String login);
}
