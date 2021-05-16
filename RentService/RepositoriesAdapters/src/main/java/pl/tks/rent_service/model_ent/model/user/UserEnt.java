package pl.tks.rent_service.model_ent.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.tks.rent_service.model_ent.model.EntityEnt;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserEnt extends EntityEnt {
    private String name;
    private String lastName;
    private boolean isActive = true;
    private String login;
    private String password;

}
