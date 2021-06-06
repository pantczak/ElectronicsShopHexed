package pl.tks.user_service.model_domain.model.user;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Administrator extends User {

    public Administrator(String name, String lastName, String login, String password) {
        super(name, lastName, true, login, password);
    }

    public String getRole() {
        return "Administrator";
    }
}
