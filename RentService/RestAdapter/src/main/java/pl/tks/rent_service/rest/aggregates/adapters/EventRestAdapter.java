package pl.tks.rent_service.rest.aggregates.adapters;


import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.rest.aggregates.mappers.EventMapper;
import pl.tks.rent_service.rest.model.EventDtoRest;
import pl.tks.rent_service.service.interfaces.EventService;
import pl.tks.rent_service.user_interfaces.AddEventPort;
import pl.tks.rent_service.user_interfaces.GetEventPort;


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
