package pl.tks.rent_service.model_domain.repositories.interfaces;

import pl.tks.rent_service.model_domain.model.Event;

import java.util.List;
import java.util.UUID;

public interface IEventRepository {
    boolean addEvent(Event event);

    Event getEvent(UUID uuid);

    List<Event> getEventsByUser(UUID uuid);

    List<Event> getEventsByDevice(UUID uuid);

    List<Event> getAllEvents();

    boolean deleteEvent(UUID uuid);

    void endEvent(UUID uuid);
}
