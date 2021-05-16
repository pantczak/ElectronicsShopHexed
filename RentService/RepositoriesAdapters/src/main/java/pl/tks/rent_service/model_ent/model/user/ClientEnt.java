package pl.tks.rent_service.model_ent.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ClientEnt extends UserEnt {


    public ClientEnt(String name, String lastName, String login, String password) {
        super(name, lastName, true, login, password);

    }

    public String getRole() {
        return "Client";
    }
}
