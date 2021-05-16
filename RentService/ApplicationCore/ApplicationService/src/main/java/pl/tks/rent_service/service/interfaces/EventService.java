package pl.tks.rent_service.service.interfaces;


import pl.tks.rent_service.model_domain.model.Event;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EventService extends Serializable {
    boolean borrowDevice(UUID deviceID, UUID userID, Date date);

    boolean borrowDevice(UUID deviceID, UUID userID);

    boolean returnDevice(Event event);

    boolean deleteEvent(Event event);

    Event getEvent(UUID uuid);

    List<Event> getEventsForClient(UUID uuid);

    List<Event> getEventsForDevice(UUID uuid);

    List<Event> getAllEvents();
}
