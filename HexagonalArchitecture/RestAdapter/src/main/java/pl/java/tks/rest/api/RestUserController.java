package pl.java.tks.rest.api;


import pl.java.tks.rest.aggregates.adapters.UserRestAdapter;
import pl.java.tks.rest.model.UserDtoRest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.UUID;

@RequestScoped
@Path("users")
public class RestUserController implements Serializable {

    private UserRestAdapter adapter;

    @Inject
    public RestUserController(UserRestAdapter adapter) {
        this.adapter = adapter;
    }

    public RestUserController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{uuid}")
    public Response getUser(@PathParam("uuid") String uuid) {
        return Response.status(Response.Status.OK)
                .entity(adapter.getUser(UUID.fromString(uuid)))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users")
    public Response getAllUsers() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAll())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("clients")
    public Response getAllClients() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAllClients())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("employees")
    public Response getAllEmployees() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAllEmployees())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("administrators")
    public Response getAllAdministrators() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAllAdministrators())
                .build();
    }


    @POST
    @Path("/admin")
    public Response createAdmin(UserDtoRest admin) {
        if (adapter.addAdministrator(admin)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/employee")
    public Response createEmployee(UserDtoRest employee) {
        if (adapter.addEmployee(employee)) {
            return Response.status(Response.Status.CREATED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("/client")
    public Response createClient(UserDtoRest client) {
        if (adapter.addClient(client)) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
