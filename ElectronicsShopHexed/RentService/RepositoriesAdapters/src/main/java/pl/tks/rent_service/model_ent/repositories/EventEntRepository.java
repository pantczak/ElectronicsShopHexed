package pl.tks.rent_service.model_ent.repositories;


import pl.tks.rent_service.model_ent.model.EventEnt;
import pl.tks.rent_service.model_ent.repositories.interfaces.IEventRepository;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class EventEntRepository implements IEventRepository, Serializable {
    private final List<EventEnt> events;

    public EventEntRepository() {
        this.events = new ArrayList<>();
    }

    @Override
    public boolean addEvent(EventEnt event) {
        synchronized (events) {
            event.setUuid(UUID.randomUUID());
            return events.add(event);
        }
    }

    @Override
    public EventEnt getEvent(UUID uuid) {
        synchronized (events) {
            return events.stream().filter(e -> e.getUuid().equals(uuid)).findFirst().orElse(null);
        }
    }

    @Override
    public List<EventEnt> getEventsByUser(UUID uuid) {
        List<EventEnt> userEvents = new ArrayList<>();
        synchronized (events) {
            for (EventEnt e : events) {
                if (e.getClient().getUuid().equals(uuid)) {
                    userEvents.add(e);
                }
            }
            return userEvents;
        }

    }

    @Override
    public List<EventEnt> getEventsByDevice(UUID uuid) {
        List<EventEnt> deviceEvents = new ArrayList<>();
        synchronized (events) {
            for (EventEnt e : events) {
                if (e.getDevice().getUuid().equals(uuid)) {
                    deviceEvents.add(e);
                }
            }
            return deviceEvents;
        }
    }

    @Override
    public List<EventEnt> getAllEvents() {
        synchronized (events) {
            return new ArrayList<>(events);
        }
    }

    @Override
    public boolean deleteEvent(UUID uuid) {
        synchronized (events) {
            getEvent(uuid).getDevice().setAvailable(true);
            return events.remove(getEvent(uuid));
        }
    }

    @Override
    public void endEvent(UUID uuid) {
        synchronized (events) {
            getEvent(uuid).setReturnDate(new Date());
        }
    }
}
