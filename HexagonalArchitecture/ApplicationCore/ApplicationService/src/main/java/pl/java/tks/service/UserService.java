package pl.java.tks.service;

import pl.java.tks.infrastructure.UserPort;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_domain.model.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class UserService implements Serializable {

    private UserPort userPort;

    public UserService() {
    }

    @Inject
    public UserService(UserPort userPort) {
        this.userPort = userPort;
    }

    public boolean addClient(Client client) {
        return addClient(client.getLogin(), client.getName(), client.getLastName(), client.getPassword(), client.getAge());
    }

    public boolean addClient(String login, String name, String lastName, String password, int age) {
        if (login == null || name == null || lastName == null || password == null || age < 0) {
            return false;
        }
        return userPort.addUser(new Client(name, lastName, login, password, age));
    }

    public boolean addEmployee(Employee employee) {
        return addEmployee(employee.getLogin(), employee.getName(), employee.getLastName(), employee.getPassword());
    }

    public boolean addEmployee(String login, String name, String lastName, String password) {
        if (login == null || name == null || lastName == null || password == null) {
            return false;
        }
        return userPort.addUser(new Employee(name, lastName, login, password));
    }

    public boolean addAdministrator(Administrator administrator) {
        return addAdministrator(administrator.getLogin(), administrator.getName(), administrator.getLastName(), administrator.getPassword());
    }


    public boolean addAdministrator(String login, String name, String lastName, String password) {
        if (login == null || name == null || lastName == null || password == null) {
            return false;
        }
        return userPort.addUser(new Administrator(name, lastName, login, password));
    }

    public User getUser(UUID uuid) {
        return userPort.getUser(uuid);
    }

    public User getUser(String login) {
        if (login == null) {
            return null;
        }
        return userPort.getUser(login);
    }

    public List<Client> getAllClients() {
        return userPort.getAllClients();
    }

    public List<User> getAllEmployees() {
        return userPort.getAllEmployees();
    }

    public List<User> getAllAdministrators() {
        return userPort.getAllAdministrators();
    }

    public List<User> getAll() {
        return userPort.getAll();
    }

    public boolean updateUser(User old, String login, String name, String lastName) {
        if (old == null || userPort.getUser(old.getLogin()) == null
                || login == null || name == null || lastName == null) {
            return false;
        }
        if (old instanceof Employee) {
            userPort.updateUser(old.getUuid(), new Employee(name, lastName, login, old.getPassword()));
        } else if (old instanceof Administrator) {
            userPort.updateUser(old.getUuid(), new Administrator(name, lastName, login, old.getPassword()));
        }
        return true;
    }

    public boolean updateClient(User old, String login, String name, String lastName, int age) {
        if (old == null || userPort.getUser(old.getLogin()) == null
                || login == null || name == null || lastName == null || age <= 0 || !(old instanceof Client)) {
            return false;
        }
        userPort.updateUser(old.getUuid(), new Client(name, lastName, login, old.getPassword(), age));
        return true;
    }

    public boolean activateUser(User user) {
        if (user == null) {
            return false;
        }
        user.setActive(true);
        userPort.updateUser(user.getUuid(),user);
        return true;
    }

    public boolean deactivateUser(User user) {
        if (user == null) {
            return false;
        }
        user.setActive(false);
        userPort.updateUser(user.getUuid(),user);
        return true;
    }

    public User getUserByLoginPassword(String login, String passwordAsString) {
        User user = null;
        try {
            User tmp = getUser(login);
            if (tmp.getPassword().equals(passwordAsString) && tmp.isActive()) {
                user = tmp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isUserActive(String login) {
        return getUser(login).isActive();
    }
}
