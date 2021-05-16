package pl.tks.user_service.input;

import pl.tks.user_service.model_domain.model.user.User;

import java.util.UUID;

public interface UpdateUserPort {
    void updateUser(UUID uuid, User newUser);
}
