package pl.java.tks.infrastructure;

import pl.java.tks.model_domain.model.user.User;

public interface AddUserPort {
    boolean addUser(User user);
}
