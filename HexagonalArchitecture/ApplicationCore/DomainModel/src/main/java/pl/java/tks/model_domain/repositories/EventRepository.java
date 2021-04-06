package pl.java.tks.model_domain.repositories;

import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.repositories.interfaces.IEventRepository;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public class EventRepository implements IEventRepository, Serializable {
    private final List<Event> events;

    public EventRepository() {
        this.events = new ArrayList<>();
    }

    @Override
    public boolean addEvent(Event event) {
        synchronized (events) {
            event.setUuid(UUID.randomUUID());
            return events.add(event);
        }
    }

    @Override
    public Event getEvent(UUID uuid) {
        synchronized (events) {
            return events.stream().filter(e -> e.getUuid().equals(uuid)).findFirst().orElse(null);
        }
    }

    @Override
    public List<Event> getEventsByUser(UUID uuid) {
        List<Event> userEvents = new ArrayList<>();
        synchronized (events) {
            for (Event e : events) {
                if (e.getClient().getUuid().equals(uuid)) {
                    userEvents.add(e);
                }
            }
            return userEvents;
        }

    }

    @Override
    public List<Event> getEventsByDevice(UUID uuid) {
        List<Event> deviceEvents = new ArrayList<>();
        synchronized (events) {
            for (Event e : events) {
                if (e.getDevice().getUuid().equals(uuid)) {
                    deviceEvents.add(e);
                }
            }
            return deviceEvents;
        }
    }

    @Override
    public List<Event> getAllEvents() {
        synchronized (events) {
            return new ArrayList<>(events);
        }
    }

    @Override
    public boolean deleteEvent(UUID uuid) {
        synchronized (events){
            getEvent(uuid).getDevice().setAvailable(true);
            return events.remove(getEvent(uuid));
        }
    }

    @Override
    public void endEvent(UUID uuid) {
        synchronized (events){
            getEvent(uuid).setReturnDate(new Date());
        }
    }
}
