package pl.java.tks.model_ent.repositories;

import pl.java.tks.model_ent.model.user.AdministratorEnt;
import pl.java.tks.model_ent.model.user.ClientEnt;
import pl.java.tks.model_ent.model.user.EmployeeEnt;
import pl.java.tks.model_ent.model.user.UserEnt;
import pl.java.tks.model_ent.repositories.interfaces.IUserRepository;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserEntRepository implements IUserRepository, Serializable {
    private final List<UserEnt> users;

    public UserEntRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public boolean addUser(UserEnt user) {
        synchronized (users) {
            if (users.contains(getUser(user.getLogin()))) return false;
            user.setUuid(UUID.randomUUID());
            return users.add(user);
        }
    }

    @Override
    public UserEnt getUser(UUID uuid) {
        synchronized (users) {
            for (UserEnt u : users) {
                if (u.getUuid().equals(uuid)) return u;
            }
            return null;
        }
    }

    @Override
    public UserEnt getUser(String login) {
        synchronized (users) {
            return users.stream().filter(u -> u.getLogin().equals(login)).findFirst().orElse(null);
        }
    }

    @Override
    public void updateUser(UUID uuid, UserEnt newUser) {
        synchronized (users) {
            for (UserEnt u : users) {
                if (u.getUuid().equals(uuid)) {
                    newUser.setUuid(uuid);
                    users.set(users.indexOf(u), newUser);
                }
            }
        }
    }

    @Override
    public List<ClientEnt> getAllClients() {
        synchronized (users) {
            ArrayList<ClientEnt> clients = new ArrayList<>();
            for (UserEnt user : users) {
                if (user instanceof ClientEnt) {
                    clients.add((ClientEnt) user);
                }
            }
            return clients;
        }
    }

    @Override
    public List<UserEnt> getAllEmployees() {
        synchronized (users) {
            ArrayList<UserEnt> employees = new ArrayList<>();
            for (UserEnt user : users) {
                if (user instanceof EmployeeEnt) {
                    employees.add(user);
                }
            }
            return employees;
        }
    }

    @Override
    public List<UserEnt> getAllAdministrators() {
        synchronized (users) {
            ArrayList<UserEnt> administrators = new ArrayList<>();
            for (UserEnt user : users) {
                if (user instanceof AdministratorEnt) {
                    administrators.add(user);
                }
            }
            return administrators;
        }
    }

    @Override
    public List<UserEnt> getAll() {
        synchronized (users) {
            return new ArrayList<>(users);
        }
    }
}
