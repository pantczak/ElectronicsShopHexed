package pl.tks.user_service.model_domain.model.user;

import java.util.UUID;

public class UserWrapper {
    private final String name;
    private final String lastName;
    private final boolean isActive;
    private final String login;
    private final String role;
    private Integer age;
    private UUID uuid;

    public UserWrapper(UUID uuid, String name, String lastName, boolean isActive, String login, int age, String role) {
        this.name = name;
        this.lastName = lastName;
        this.isActive = isActive;
        this.login = login;
        this.age = age;
        this.uuid = uuid;
        this.role = role;
    }

    public UserWrapper(UUID uuid, String name, String lastName, boolean isActive, String login,String role) {
        this.name = name;
        this.lastName = lastName;
        this.isActive = isActive;
        this.login = login;
        this.uuid = uuid;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getLogin() {
        return login;
    }

    public Integer getAge() {
        return age;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getRole() {
        return role;
    }
}
