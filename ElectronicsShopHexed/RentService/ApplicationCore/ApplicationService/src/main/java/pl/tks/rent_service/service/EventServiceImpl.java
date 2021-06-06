package pl.tks.rent_service.service;

import pl.tks.rent_service.infrastructure.DevicePort;
import pl.tks.rent_service.infrastructure.EventPort;
import pl.tks.rent_service.infrastructure.UserPort;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.User;
import pl.tks.rent_service.service.interfaces.EventService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventServiceImpl implements EventService {


    private DevicePort devicePort;

    private EventPort eventPort;

    private UserPort userPort;

    public EventServiceImpl() {
    }

    @Inject
    public EventServiceImpl(DevicePort devicePort, EventPort eventPort,
                            UserPort userPort) {
        this.devicePort = devicePort;
        this.eventPort = eventPort;
        this.userPort = userPort;
    }

    @Override
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

    @Override
    public boolean borrowDevice(UUID deviceID, UUID userID) {
        return borrowDevice(deviceID, userID, new Date());
    }

    @Override
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

    @Override
    public boolean deleteEvent(Event event) {
        if (event == null || event.getReturnDate() == null || eventPort.getEvent(event.getUuid()) == null) {
            return false;
        }
        return eventPort.deleteEvent(event.getUuid());
    }

    @Override
    public Event getEvent(UUID uuid) {
        return eventPort.getEvent(uuid);
    }

    @Override
    public List<Event> getEventsForClient(UUID uuid) {
        return eventPort.getEventsByUser(uuid);
    }

    @Override
    public List<Event> getEventsForDevice(UUID uuid) {
        return eventPort.getEventsByDevice(uuid);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventPort.getAllEvents();
    }


}
