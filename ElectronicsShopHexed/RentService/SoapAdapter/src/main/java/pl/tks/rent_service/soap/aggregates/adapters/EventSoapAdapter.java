package pl.tks.rent_service.soap.aggregates.adapters;


import pl.tks.rent_service.service.interfaces.EventService;
import pl.tks.rent_service.soap.aggregates.mappers.EventMapper;
import pl.tks.rent_service.soap.model.EventDtoSoap;
import pl.tks.rent_service.user_interfaces.GetEventPort;


import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class EventSoapAdapter implements GetEventPort<EventDtoSoap> {


    private final EventService service;

    @Inject
    public EventSoapAdapter(EventService service) {
        this.service = service;
    }

    @Override
    public EventDtoSoap getEvent(UUID uuid) {
        return EventMapper.INSTANCE.eventToEventDto(service.getEvent(uuid));
    }

    @Override
    public List<EventDtoSoap> getEventsByUser(UUID uuid) {
        return service.getEventsForClient(uuid).stream()
                .map(EventMapper.INSTANCE::eventToEventDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<EventDtoSoap> getEventsByDevice(UUID uuid) {
        return service.getEventsForDevice(uuid).stream()
                .map(EventMapper.INSTANCE::eventToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDtoSoap> getAllEvents() {
        return service.getAllEvents().stream()
                .map(EventMapper.INSTANCE::eventToEventDto)
                .collect(Collectors.toList());
    }
}
