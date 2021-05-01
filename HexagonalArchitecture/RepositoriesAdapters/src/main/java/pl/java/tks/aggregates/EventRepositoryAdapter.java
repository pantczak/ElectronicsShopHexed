package pl.java.tks.aggregates;

import pl.java.tks.aggregates.converters.EventConverter;
import pl.java.tks.infrastructure.EventPort;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_ent.repositories.EventEntRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Dependent
public class EventRepositoryAdapter implements EventPort {

    private final EventEntRepository repository;
//    private ModelEntConverter converter;

    @PostConstruct
    private void init() {
        Laptop laptop = new Laptop("Xiaomi", "MiAir", 1290, 8);
        laptop.setUuid(UUID.randomUUID());
        Client client = new Client("Dokor", "Nauk", "rodzyn123", "pasowd123", 15);
        client.setUuid(UUID.randomUUID());
        addEvent(new Event(laptop,client));

    }


    @Inject
    public EventRepositoryAdapter(EventEntRepository repository) {
        this.repository = repository;
//        converter = new ModelEntConverter();
    }

    @Override
    public boolean addEvent(Event event) {
        return repository.addEvent(EventConverter.convertEventToEnt(event));
    }

    @Override
    public boolean deleteEvent(UUID uuid) {
        return repository.deleteEvent(uuid);
    }

    @Override
    public void endEvent(UUID uuid) {
        repository.endEvent(uuid);

    }

    @Override
    public Event getEvent(UUID uuid) {
        return EventConverter.convertEntToEvent(repository.getEvent(uuid));
    }

    @Override
    public List<Event> getEventsByUser(UUID uuid) {

//        List<Event> events = new LinkedList<>();
//        for (EventEnt eventEnt : repository.getEventsByUser(uuid)) {
//            events.add(EventConverter.convertEntToEvent(eventEnt));
//        }
//        return events;

        return repository.getEventsByUser(uuid).stream().map(EventConverter::convertEntToEvent).collect(Collectors.toList());

    }

    @Override
    public List<Event> getEventsByDevice(UUID uuid) {
//        List<Event> events = new LinkedList<>();
//        for (EventEnt eventEnt : repository.getEventsByDevice(uuid)) {
//            events.add(EventConverter.convertEntToEvent(eventEnt));
//        }
//        return events;

        return repository.getEventsByDevice(uuid).stream().map(EventConverter::convertEntToEvent).collect(Collectors.toList());
    }

    @Override
    public List<Event> getAllEvents() {
//        List<Event> events = new LinkedList<>();
//        for (EventEnt eventEnt : repository.getAllEvents()) {
//            events.add(EventConverter.convertEntToEvent(eventEnt));
//        }
//        return events;
        return repository.getAllEvents().stream().map(EventConverter::convertEntToEvent).collect(Collectors.toList());
    }
}
