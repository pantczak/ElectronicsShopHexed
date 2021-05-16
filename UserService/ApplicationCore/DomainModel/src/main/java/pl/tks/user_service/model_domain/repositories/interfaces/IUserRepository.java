package pl.tks.user_service.model_domain.repositories.interfaces;

import pl.tks.user_service.model_domain.model.user.Client;
import pl.tks.user_service.model_domain.model.user.User;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {
    boolean addUser(User user);

    User getUser(UUID uuid);

    User getUser(String login);

    void updateUser(UUID uuid, User newUser);

    List<Client> getAllClients();

    List<User> getAllEmployees();

    List<User> getAllAdministrators();

    List<User> getAll();
}
