package pl.java.tks.model_domain.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Client extends User {
    private int age;

    public Client(String name, String lastName, String login, String password, int age) {
        super(name, lastName, true, login, password);
        this.age = age;
    }

    public String getRole() {
        return "Client";
    }
}
