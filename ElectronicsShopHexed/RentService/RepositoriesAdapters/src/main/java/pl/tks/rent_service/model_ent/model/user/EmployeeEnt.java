package pl.tks.rent_service.model_ent.model.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmployeeEnt extends UserEnt {
    public EmployeeEnt(String name, String lastName, String login, String password) {
        super(name, lastName, true, login, password);
    }

    public String getRole() {
        return "Employee";
    }
}
