package pl.tks.user_service.model_domain.model.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {

    public Employee(String name, String lastName, String login, String password) {
        super(name, lastName, true, login, password);
    }

    public String getRole() {
        return "Employee";
    }
}
