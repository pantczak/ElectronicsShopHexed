package pl.tks.user_service.input;

import pl.tks.user_service.model_domain.model.user.User;

public interface AddUserPort {
    boolean addUser(User user);
}
