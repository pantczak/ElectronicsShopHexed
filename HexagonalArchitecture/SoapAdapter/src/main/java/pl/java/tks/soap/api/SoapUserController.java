package pl.java.tks.soap.api;


import pl.java.tks.soap.aggregates.adapters.UserSoapAdapter;
import pl.java.tks.soap.model.UserDtoSoap;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@WebService(serviceName = "UserAPI")
public class SoapUserController {

    private UserSoapAdapter adapter;

    @Inject
    public SoapUserController(UserSoapAdapter adapter) {
        this.adapter = adapter;
    }

    public SoapUserController() {
    }

    @WebMethod
    public List<UserDtoSoap> getAllUsers() {
        return adapter.getAll();
    }

    @WebMethod
    public UserDtoSoap getUserByUuid(String uuid) {
        return adapter.getUser(UUID.fromString(uuid));
    }

    @WebMethod
    public UserDtoSoap getUserByLogin(String login) {
        return adapter.getUser(login);
    }

    @WebMethod
    public List<UserDtoSoap> getAllEmployees() {
        return adapter.getAllEmployees();
    }

    @WebMethod
    public List<UserDtoSoap> getAllAdministrators() {
        return adapter.getAllAdministrators();
    }

    @WebMethod
    public List<UserDtoSoap> getAllClients() {
        return adapter.getAllClients();
    }

}
