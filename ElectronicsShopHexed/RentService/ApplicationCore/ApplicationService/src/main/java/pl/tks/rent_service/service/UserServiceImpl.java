package pl.tks.rent_service.service;

import pl.tks.rent_service.infrastructure.UserPort;
import pl.tks.rent_service.model_domain.model.user.Administrator;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.Employee;
import pl.tks.rent_service.model_domain.model.user.User;
import pl.tks.rent_service.service.interfaces.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class UserServiceImpl implements UserService {

    private UserPort userPort;

    public UserServiceImpl() {
    }

    @Inject
    public UserServiceImpl(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public boolean addClient(Client client) {
        return addClient(client.getLogin(), client.getName(), client.getLastName(), client.getPassword());
    }

    @Override
    public boolean addClient(String login, String name, String lastName, String password) {
        if (login == null || name == null || lastName == null || password == null) {
            return false;
        }
        return userPort.addUser(new Client(name, lastName, login, password ));
    }

    @Override
    public boolean addEmployee(Employee employee) {
        return addEmployee(employee.getLogin(), employee.getName(), employee.getLastName(), employee.getPassword());
    }

    @Override
    public boolean addEmployee(String login, String name, String lastName, String password) {
        if (login == null || name == null || lastName == null || password == null) {
            return false;
        }
        return userPort.addUser(new Employee(name, lastName, login, password));
    }

    @Override
    public boolean addAdministrator(Administrator administrator) {
        return addAdministrator(administrator.getLogin(), administrator.getName(), administrator.getLastName(), administrator.getPassword());
    }


    @Override
    public boolean addAdministrator(String login, String name, String lastName, String password) {
        if (login == null || name == null || lastName == null || password == null) {
            return false;
        }
        return userPort.addUser(new Administrator(name, lastName, login, password));
    }

    @Override
    public User getUser(UUID uuid) {
        return userPort.getUser(uuid);
    }

    @Override
    public User getUser(String login) {
        if (login == null) {
            return null;
        }
        return userPort.getUser(login);
    }

    @Override
    public List<Client> getAllClients() {
        return userPort.getAllClients();
    }

    @Override
    public List<User> getAllEmployees() {
        return userPort.getAllEmployees();
    }

    @Override
    public List<User> getAllAdministrators() {
        return userPort.getAllAdministrators();
    }

    @Override
    public List<User> getAll() {
        return userPort.getAll();
    }

    @Override
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

    @Override
    public boolean updateClient(User old, String login, String name, String lastName) {
        if (old == null || userPort.getUser(old.getLogin()) == null
                || login == null || name == null || lastName == null ||!(old instanceof Client)) {
            return false;
        }
        userPort.updateUser(old.getUuid(), new Client(name, lastName, login, old.getPassword()));
        return true;
    }

    @Override
    public boolean activateUser(User user) {
        if (user == null) {
            return false;
        }
        user.setActive(true);
        userPort.updateUser(user.getUuid(),user);
        return true;
    }

    @Override
    public boolean deactivateUser(User user) {
        if (user == null) {
            return false;
        }
        user.setActive(false);
        userPort.updateUser(user.getUuid(),user);
        return true;
    }

    @Override
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

    @Override
    public boolean isUserActive(String login) {
        return getUser(login).isActive();
    }
}
