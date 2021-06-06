package pl.tks.user_service.model_ent.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ClientEnt extends UserEnt {
    private int age;

    public ClientEnt(String name, String lastName, String login, String password, int age) {
        super(name, lastName, true, login, password);
        this.age = age;
    }

    public String getRole() {
        return "Client";
    }
}
