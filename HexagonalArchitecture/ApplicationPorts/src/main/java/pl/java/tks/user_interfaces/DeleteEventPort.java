package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.Event;

import java.util.UUID;

public interface DeleteEventPort<T1> {
    boolean deleteEvent(T1 event);
}
