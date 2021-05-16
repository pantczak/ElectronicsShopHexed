package pl.tks.rent_service.soap.api;


import pl.tks.rent_service.soap.aggregates.adapters.EventSoapAdapter;
import pl.tks.rent_service.soap.model.EventDtoSoap;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;
import java.util.UUID;

@WebService(serviceName = "EventAPI")
public class SoapEventController {

    private EventSoapAdapter eventSoapAdapter;

    @Inject
    public SoapEventController(EventSoapAdapter eventSoapAdapter) {
        this.eventSoapAdapter = eventSoapAdapter;
    }

    public SoapEventController() {
    }

    @WebMethod
    public EventDtoSoap getEvent(String uuid) {
        return eventSoapAdapter.getEvent(UUID.fromString(uuid));
    }

    @WebMethod
    public List<EventDtoSoap> getAllEvents() {
        return eventSoapAdapter.getAllEvents();
    }

    @WebMethod
    public List<EventDtoSoap> getEventsByDevice(String uuid) {
        return eventSoapAdapter.getEventsByDevice(UUID.fromString(uuid));
    }

    @WebMethod
    public List<EventDtoSoap> getEventsByUser(String uuid) {
        return eventSoapAdapter.getEventsByUser(UUID.fromString(uuid));
    }


}
