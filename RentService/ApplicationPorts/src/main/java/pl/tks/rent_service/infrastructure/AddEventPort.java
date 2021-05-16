package pl.tks.rent_service.infrastructure;


import pl.tks.rent_service.model_domain.model.Event;

public interface AddEventPort {
    boolean addEvent(Event event);
}
