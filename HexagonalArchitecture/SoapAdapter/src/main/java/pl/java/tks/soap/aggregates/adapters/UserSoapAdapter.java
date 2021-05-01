package pl.java.tks.soap.aggregates.adapters;

import pl.java.tks.user_interfaces.GetUserPort;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_domain.model.user.User;
import pl.java.tks.service.UserService;
import pl.java.tks.soap.aggregates.mappers.UserMapper;
import pl.java.tks.soap.model.UserDtoSoap;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class UserSoapAdapter implements GetUserPort<UserDtoSoap, UserDtoSoap> {

    private final UserService service;

    @Inject
    public UserSoapAdapter(UserService service) {
        this.service = service;
    }

    @Override
    public UserDtoSoap getUser(UUID uuid) {
        User user = service.getUser((uuid));
        if (user instanceof Client) {
            return UserMapper.INSTANCE.clientToUserDto((Client) user);
        } else if (user instanceof Employee) {
            return UserMapper.INSTANCE.employeeToUserDto((Employee) user);
        }
        return UserMapper.INSTANCE.administratorToUserDto((Administrator) user);
    }

    @Override
    public UserDtoSoap getUser(String login) {
        return getUser(service.getUser((login)).getUuid());
    }

    @Override
    public List<UserDtoSoap> getAllClients() {
        return service.getAllClients().stream()
                .map(UserMapper.INSTANCE::clientToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDtoSoap> getAllEmployees() {
        return service.getAllEmployees().stream()
                .map(user -> (Employee) user)
                .map(UserMapper.INSTANCE::employeeToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDtoSoap> getAllAdministrators() {
        return service.getAllAdministrators().stream()
                .map(user -> (Administrator) user)
                .map(UserMapper.INSTANCE::administratorToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDtoSoap> getAll() {
        List<UserDtoSoap> userDtoSoaps = new ArrayList<>();
        for (User user : service.getAll()) {
            if (user instanceof Client) {
                userDtoSoaps.add(UserMapper.INSTANCE.clientToUserDto((Client) user));
            } else if (user instanceof Employee) {
                userDtoSoaps.add(UserMapper.INSTANCE.employeeToUserDto((Employee) user));
            } else {
                userDtoSoaps.add(UserMapper.INSTANCE.administratorToUserDto((Administrator) user));
            }
        }
        return userDtoSoaps;
    }
}
