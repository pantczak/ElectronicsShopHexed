package pl.tks.rent_service.rest.model;

import lombok.Data;

@Data
public class UserDtoRest {
    private String uuid;
    private String name;
    private String lastName;
    private boolean isActive = true;
    private String login;
    private String password;
}
