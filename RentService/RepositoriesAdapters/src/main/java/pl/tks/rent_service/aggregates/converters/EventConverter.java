package pl.tks.rent_service.aggregates.converters;


import pl.tks.rent_service.model_ent.model.EventEnt;
import pl.tks.rent_service.model_ent.model.user.ClientEnt;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.user.Client;

public class EventConverter {
    private EventConverter() {
    }

    public static EventEnt convertEventToEnt(Event event) {
        EventEnt result = null;
        if (event != null) {
            result = new EventEnt(DeviceConverter.convertDeviceToEnt(event.getDevice()), (ClientEnt) UserConverter.convertUserToEnt(event.getClient()), event.getBorrowDate());
            result.setReturnDate(event.getReturnDate());
            result.setUuid(event.getUuid());
        }
        return result;
    }

    public static Event convertEntToEvent(EventEnt eventEnt) {
        Event result = null;
        if (eventEnt != null) {
            result = new Event(DeviceConverter.convertEntToDevice(eventEnt.getDevice()), (Client) UserConverter.convertEntToUser(eventEnt.getClient()), eventEnt.getBorrowDate());
            result.setReturnDate(eventEnt.getReturnDate());
            result.setUuid(eventEnt.getUuid());
        }
        return result;
    }
}
