package pl.java.tks.rest.aggregates.adapters;

import pl.java.tks.model_domain.model.Event;
import pl.java.tks.rest.aggregates.mappers.EventMapper;
import pl.java.tks.rest.model.EventDtoRest;
import pl.java.tks.service.EventService;
import pl.java.tks.user_interfaces.AddEventPort;
import pl.java.tks.user_interfaces.DeleteEventPort;
import pl.java.tks.user_interfaces.EndEventPort;
import pl.java.tks.user_interfaces.GetEventPort;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class EventRestAdapter implements GetEventPort<EventDtoRest>,
        AddEventPort<EventDtoRest> {

    private final EventService service;

    @Inject
    private EventMapper mapper;

    @Inject
    public EventRestAdapter(EventService service) {
        this.service = service;
    }

    @Override
    public EventDtoRest getEvent(UUID uuid) {
        return mapper.eventToEventDto(service.getEvent(uuid));
    }

    @Override
    public List<EventDtoRest> getEventsByUser(UUID uuid) {
        return service.getEventsForClient(uuid).stream()
                .map(mapper::eventToEventDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<EventDtoRest> getEventsByDevice(UUID uuid) {
        return service.getEventsForDevice(uuid).stream()
                .map(mapper::eventToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDtoRest> getAllEvents() {
        return service.getAllEvents().stream()
                .map(mapper::eventToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addEvent(EventDtoRest eventDto) {
        Event event = mapper.toEvent(eventDto);
        return service.borrowDevice(event.getDevice().getUuid(), event.getClient().getUuid());
    }
}
