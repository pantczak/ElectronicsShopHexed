package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.user.User;

import java.util.UUID;

public interface UpdateUserPort {
    void updateUser(UUID uuid, User newUser);
}
