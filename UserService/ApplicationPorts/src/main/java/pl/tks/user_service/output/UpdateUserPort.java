package pl.tks.user_service.output;


import pl.tks.user_service.model_domain.model.user.User;

import java.util.UUID;

public interface UpdateUserPort {
    void updateUser(UUID uuid, User newUser);
}
