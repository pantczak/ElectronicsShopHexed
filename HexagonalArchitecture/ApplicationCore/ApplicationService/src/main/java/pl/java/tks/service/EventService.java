package pl.java.tks.service;

import pl.java.tks.infrastructure.DevicePort;
import pl.java.tks.infrastructure.EventPort;
import pl.java.tks.infrastructure.UserPort;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventService implements Serializable {


    private DevicePort devicePort;

    private EventPort eventPort;

    private UserPort userPort;

    public EventService() {
    }

    @Inject
    public EventService(DevicePort devicePort, EventPort eventPort,
                        UserPort userPort) {
        this.devicePort = devicePort;
        this.eventPort = eventPort;
        this.userPort = userPort;
    }

    public boolean borrowDevice(UUID deviceID, UUID userID, Date date) {
        Device device = devicePort.getDevice(deviceID);
        User user = userPort.getUser(userID);
        if (device == null || user == null) {
            return false;
        }
        if (device.isAvailable() && user instanceof Client && user.isActive()) {
            eventPort.addEvent(new Event(device, (Client) user, date));
            device.setAvailable(false);
            devicePort.updateDevice(device.getUuid(),device);
            return true;
        }
        return false;
    }

    public boolean borrowDevice(UUID deviceID, UUID userID) {
        return borrowDevice(deviceID, userID, new Date());
    }

    public boolean returnDevice(Event event) {
        if (event == null || event.getReturnDate() != null || eventPort.getEvent(event.getUuid()) == null) {
            return false;
        }
        eventPort.endEvent(event.getUuid());
        Device device = event.getDevice();
        device.setAvailable(true);
        devicePort.updateDevice(device.getUuid(),device);
        return true;
    }

    public boolean deleteEvent(Event event) {
        if (event == null || event.getReturnDate() == null || eventPort.getEvent(event.getUuid()) == null) {
            return false;
        }
        return eventPort.deleteEvent(event.getUuid());
    }

    public Event getEvent(UUID uuid) {
        return eventPort.getEvent(uuid);
    }

    public List<Event> getEventsForClient(UUID uuid) {
        return eventPort.getEventsByUser(uuid);
    }

    public List<Event> getEventsForDevice(UUID uuid) {
        return eventPort.getEventsByDevice(uuid);
    }

    public List<Event> getAllEvents() {
        return eventPort.getAllEvents();
    }


}
