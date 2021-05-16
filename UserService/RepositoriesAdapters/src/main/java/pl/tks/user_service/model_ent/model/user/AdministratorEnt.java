package pl.tks.user_service.model_ent.model.user;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor

public class AdministratorEnt extends UserEnt {

    public AdministratorEnt(String name, String lastName, String login, String password) {
        super(name, lastName, true, login, password);
    }

    public String getRole() {
        return "Administrator";
    }
}
