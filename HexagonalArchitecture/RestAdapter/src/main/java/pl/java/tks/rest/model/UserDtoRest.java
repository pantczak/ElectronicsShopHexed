package pl.java.tks.rest.model;

import lombok.Data;

@Data
public class UserDtoRest {
    private String uuid;
    private String name;
    private String lastName;
    private boolean isActive = true;
    private String login;
    private String password;
    private int age;
}
