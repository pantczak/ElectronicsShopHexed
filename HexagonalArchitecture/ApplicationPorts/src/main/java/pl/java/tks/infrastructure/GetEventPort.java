package pl.java.tks.infrastructure;

import pl.java.tks.model_domain.model.Event;

import java.util.List;
import java.util.UUID;

public interface GetEventPort {
    Event getEvent(UUID uuid);

    List<Event> getEventsByUser(UUID uuid);

    List<Event> getEventsByDevice(UUID uuid);

    List<Event> getAllEvents();
}
