package pl.java.tks.infrastructure;

import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.User;

import java.util.List;
import java.util.UUID;

public interface GetUserPort {
    User getUser(UUID uuid);

    User getUser(String login);

    List<Client> getAllClients();

    List<User> getAllEmployees();

    List<User> getAllAdministrators();

    List<User> getAll();
}
