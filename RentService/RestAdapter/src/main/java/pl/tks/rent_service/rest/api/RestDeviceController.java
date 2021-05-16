package pl.tks.rent_service.rest.api;

import pl.tks.rent_service.rest.aggregates.adapters.DeviceRestAdapter;
import pl.tks.rent_service.rest.model.DeviceDtoRest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.UUID;

@Path("/devices")
@RequestScoped
public class RestDeviceController implements Serializable {

    private DeviceRestAdapter adapter;

    @Inject
    public RestDeviceController(DeviceRestAdapter adapter) {
        this.adapter = adapter;
    }

    public RestDeviceController() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{uuid}")
    public Response getDevice(@PathParam("uuid") String uuid) {
        return Response.status(Response.Status.OK)
                .entity(adapter.getDevice(UUID.fromString(uuid)))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public Response getAllDevices() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAllDevices())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("laptops")
    public Response getAllLaptops() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAllLaptops())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("smartphones")
    public Response getAllSmartphones() {
        return Response.status(Response.Status.OK)
                .entity(adapter.getAllSmartphones())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add/smartphone")
    public Response addSmartphone(DeviceDtoRest smartphoneDto) {
        if (adapter.addSmartphone(smartphoneDto)) return Response.status(Response.Status.CREATED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("add/laptop")
    public Response addLaptop(DeviceDtoRest laptopDto) {
        if (adapter.addLaptop(laptopDto)) return Response.status(Response.Status.CREATED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{uuid}")
    public Response deleteDevice(@PathParam("uuid") String uuid) {
        if (adapter.deleteDevice(UUID.fromString(uuid))) return Response.status(Response.Status.NO_CONTENT).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }



}
