package pl.tks.rent_service.model_domain.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Client extends User {

    public Client(String name, String lastName, String login, String password) {
        super(name, lastName, true, login, password);
    }

    public String getRole() {
        return "Client";
    }
}
