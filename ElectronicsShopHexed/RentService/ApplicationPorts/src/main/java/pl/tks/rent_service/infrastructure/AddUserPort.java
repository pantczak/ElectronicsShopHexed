package pl.tks.rent_service.infrastructure;

import pl.tks.rent_service.model_domain.model.user.User;

public interface AddUserPort {
    boolean addUser(User user);
}
