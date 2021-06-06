package pl.tks.rent_service.rest.aggregates.adapters;


import pl.tks.rent_service.model_domain.model.user.Administrator;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.Employee;
import pl.tks.rent_service.model_domain.model.user.User;
import pl.tks.rent_service.rest.aggregates.mappers.UserMapper;
import pl.tks.rent_service.rest.model.UserDtoRest;
import pl.tks.rent_service.service.interfaces.UserService;
import pl.tks.rent_service.user_interfaces.AddUserPort;
import pl.tks.rent_service.user_interfaces.GetUserPort;


import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class UserRestAdapter implements GetUserPort<UserDtoRest, UserDtoRest>, AddUserPort<UserDtoRest> {

    private final UserService service;


    @Inject
    private UserMapper mapper;

    @Inject
    public UserRestAdapter(UserService service) {
        this.service = service;
    }

    @Override
    public UserDtoRest getUser(UUID uuid) {
        User user = service.getUser((uuid));
        if (user instanceof Client) {
            return mapper.clientToUserDto((Client) user);
        } else if (user instanceof Employee) {
            return mapper.employeeToUserDto((Employee) user);
        }
        return mapper.administratorToUserDto((Administrator) user);
    }

    @Override
    public UserDtoRest getUser(String login) {
        return getUser(service.getUser((login)).getUuid());
    }

    @Override
    public List<UserDtoRest> getAllClients() {
        return service.getAllClients().stream()
                .map(mapper::clientToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDtoRest> getAllEmployees() {
        return service.getAllEmployees().stream()
                .map(user -> (Employee) user)
                .map(mapper::employeeToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDtoRest> getAllAdministrators() {
        return service.getAllAdministrators().stream()
                .map(user -> (Administrator) user)
                .map(mapper::administratorToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDtoRest> getAll() {
        List<UserDtoRest> userDtoSoaps = new ArrayList<>();
        for (User user : service.getAll()) {
            if (user instanceof Client) {
                userDtoSoaps.add(mapper.clientToUserDto((Client) user));
            } else if (user instanceof Employee) {
                userDtoSoaps.add(mapper.employeeToUserDto((Employee) user));
            } else {
                userDtoSoaps.add(mapper.administratorToUserDto((Administrator) user));
            }
        }
        return userDtoSoaps;
    }

    @Override
    public boolean addClient(UserDtoRest user) {
        return service.addClient(mapper.toClient(user));
    }

    @Override
    public boolean addEmployee(UserDtoRest employee) {
        return service.addEmployee(mapper.toEmployee(employee));
    }

    @Override
    public boolean addAdministrator(UserDtoRest admin) {
        return service.addAdministrator(mapper.toAdministrator(admin));
    }
}
