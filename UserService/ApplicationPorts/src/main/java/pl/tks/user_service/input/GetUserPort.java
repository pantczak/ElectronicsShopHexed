package pl.tks.user_service.input;



import pl.tks.user_service.model_domain.model.user.Client;
import pl.tks.user_service.model_domain.model.user.User;

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
