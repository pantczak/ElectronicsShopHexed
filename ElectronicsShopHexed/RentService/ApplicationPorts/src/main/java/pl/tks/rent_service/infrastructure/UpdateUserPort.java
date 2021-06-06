package pl.tks.rent_service.infrastructure;

import pl.tks.rent_service.model_domain.model.user.User;

import java.util.UUID;

public interface UpdateUserPort {
    void updateUser(UUID uuid, User newUser);
}
