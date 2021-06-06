package pl.tks.rent_service.rest.api;

import pl.tks.rent_service.rest.aggregates.adapters.EventRestAdapter;
import pl.tks.rent_service.rest.model.EventDtoRest;

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
@Path("/events")
public class RestEventController implements Serializable {

    private EventRestAdapter adapter;

    @Inject
    public RestEventController(EventRestAdapter adapter) {
        this.adapter = adapter;
    }

    public RestEventController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/event/{uuid}")
    public Response getEvent(@PathParam("uuid") String uuid) {
        return Response.status(Response.Status.OK).entity(adapter.getEvent(UUID.fromString(uuid))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public Response getAllEvents() {
        return Response.status(Response.Status.OK).entity(adapter.getAllEvents()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("device/{uuid}")
    public Response getEventsByDevice(@PathParam("uuid") String uuid) {
        return Response.status(Response.Status.OK).entity(adapter.getEventsByDevice(UUID.fromString(uuid))).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/{uuid}")
    public Response getEventsByUser(@PathParam("uuid") String uuid) {
        return Response.status(Response.Status.OK).entity(adapter.getEventsByUser(UUID.fromString(uuid))).build();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add/event")
    public Response borrowDevice(EventDtoRest eventDtoRest) {

        if (adapter.addEvent(eventDtoRest)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
