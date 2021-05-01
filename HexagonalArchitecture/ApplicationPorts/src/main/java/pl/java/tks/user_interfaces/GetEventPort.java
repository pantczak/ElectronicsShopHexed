package pl.java.tks.user_interfaces;

import java.util.List;
import java.util.UUID;

public interface GetEventPort<T1> {
    T1 getEvent(UUID uuid);

    List<T1> getEventsByUser(UUID uuid);

    List<T1> getEventsByDevice(UUID uuid);

    List<T1> getAllEvents();
}
