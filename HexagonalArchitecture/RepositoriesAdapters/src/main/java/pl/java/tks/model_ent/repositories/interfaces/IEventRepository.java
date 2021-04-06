package pl.java.tks.model_ent.repositories.interfaces;

import pl.java.tks.model_ent.model.EventEnt;

import java.util.List;
import java.util.UUID;

public interface IEventRepository {
    boolean addEvent(EventEnt event);

    EventEnt getEvent(UUID uuid);

    List<EventEnt> getEventsByUser(UUID uuid);

    List<EventEnt> getEventsByDevice(UUID uuid);

    List<EventEnt> getAllEvents();

    boolean deleteEvent(UUID uuid);

    void endEvent(UUID uuid);
}
