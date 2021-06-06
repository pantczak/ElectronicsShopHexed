package pl.tks.rent_service.user_interfaces;


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