package pl.tks.rent_service.soap.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDtoSoap", propOrder = {
        "uuid",
        "name",
        "lastName",
        "isActive",
        "login",
        "password",
})
public class UserDtoSoap {
    private String uuid;
    private String name;
    private String lastName;
    private boolean isActive = true;
    private String login;
    private String password;
}
