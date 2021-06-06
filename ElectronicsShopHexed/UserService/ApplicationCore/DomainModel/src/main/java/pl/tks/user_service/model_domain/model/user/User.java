package pl.tks.user_service.model_domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.tks.user_service.model_domain.model.Entity;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User extends Entity {

    private String name;
    private String lastName;
    private boolean isActive = true;
    private String login;
    private String password;

}
