package pl.java.tks.infrastructure;

import pl.java.tks.model_domain.model.Event;

public interface AddEventPort {
    boolean addEvent(Event event);
}
