package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.user.User;

public interface AddUserPort<T1> {
    boolean addClient(T1 user);

    boolean addEmployee(T1 employee);

    boolean addAdministrator(T1 admin);
}
