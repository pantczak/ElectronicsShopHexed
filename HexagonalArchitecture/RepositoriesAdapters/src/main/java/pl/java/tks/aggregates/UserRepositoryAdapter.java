package pl.java.tks.aggregates;

import pl.java.tks.aggregates.converters.UserConverter;
import pl.java.tks.infrastructure.UserPort;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_domain.model.user.User;
import pl.java.tks.model_ent.repositories.UserEntRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class UserRepositoryAdapter implements UserPort {

    private final UserEntRepository repository;

    @PostConstruct
    private void init(){
        addUser(new Client("Dokor","Nauk","rodzyn123","pasowd123",15));
        addUser(new Employee("Adam","123","dfsd","pasowd123"));
        addUser(new Administrator("Pan","Admin","dlaCiebie","admin123123"));
    }

    @Inject
    public UserRepositoryAdapter(UserEntRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean addUser(User user) {
        return repository.addUser(UserConverter.convertUserToEnt(user));
    }

    @Override
    public User getUser(UUID uuid) {
        return UserConverter.convertEntToUser(repository.getUser(uuid));
    }

    @Override
    public User getUser(String login) {
        return UserConverter.convertEntToUser(repository.getUser(login));
    }

    @Override
    public List<Client> getAllClients() {
        return repository.getAllClients().stream().map(UserConverter::convertEntToUser).map(Client.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllEmployees() {
        return repository.getAllEmployees().stream().map(UserConverter::convertEntToUser).map(Employee.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<User> getAllAdministrators() {
        return repository.getAllAdministrators().stream().map(UserConverter::convertEntToUser).map(Administrator.class::cast).collect(Collectors.toList());
    }

    @Override
    public List<User> getAll() {
        return repository.getAll().stream().map(UserConverter::convertEntToUser).collect(Collectors.toList());
    }

    @Override
    public void updateUser(UUID uuid, User newUser) {
        repository.updateUser(uuid, UserConverter.convertUserToEnt(newUser));
    }
}
