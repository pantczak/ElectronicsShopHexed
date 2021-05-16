package pl.tks.rent_service.model_ent.repositories.interfaces;

import pl.tks.rent_service.model_ent.model.user.ClientEnt;
import pl.tks.rent_service.model_ent.model.user.UserEnt;

import java.util.List;
import java.util.UUID;

public interface IUserRepository {
    boolean addUser(UserEnt user);

    UserEnt getUser(UUID uuid);

    UserEnt getUser(String login);

    void updateUser(UUID uuid, UserEnt newUser);

    List<ClientEnt> getAllClients();

    List<UserEnt> getAllEmployees();

    List<UserEnt> getAllAdministrators();

    List<UserEnt> getAll();
}
