package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.User;

import java.util.List;
import java.util.UUID;

public interface GetUserPort<T1,T2> {
    T1 getUser(UUID uuid);

    T1 getUser(String login);

    List<T2> getAllClients();

    List<T1> getAllEmployees();

    List<T1> getAllAdministrators();

    List<T1> getAll();
}