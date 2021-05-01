package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.Event;

public interface AddEventPort<T1> {
    boolean addEvent(T1 event);
}
