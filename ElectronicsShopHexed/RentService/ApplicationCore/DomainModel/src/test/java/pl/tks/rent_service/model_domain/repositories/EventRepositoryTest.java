package pl.tks.rent_service.model_domain.repositories;

import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventRepositoryTest {

    private final static int REPOSITORY_SIZE = 3;
    private final static int CLIENT_EVENT_COUNT = 3;
    private final static int SMARTPHONE_EVENT_COUNT = 1;
    private final static int LAPTOP_EVENT_COUNT = 2;

    private EventRepository repository;
    private final Client client = new Client("Adam", "Kowalski", "adam123", "password");
    private final Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 400, 6.6);
    private final Laptop laptop = new Laptop("Samsung", "L20", 1266, 8);

    private final Event event = new Event(smartphone, client, new Date());
    private final Event event1 = new Event(laptop, client, new Date());
    private final Event event2 = new Event(laptop, client, new Date());
    ;

    @BeforeAll
    void initEntitiesUUID() {
        client.setUuid(UUID.randomUUID());
        laptop.setUuid(UUID.randomUUID());
        smartphone.setUuid(UUID.randomUUID());
    }

    @BeforeEach
    void initialize() {

        repository = new EventRepository();
        assertTrue(repository.getAllEvents().isEmpty());
        repository.addEvent(event);
        repository.addEvent(event1);
        repository.addEvent(event2);
    }


    @Test
    void addEvent() {
        Laptop laptop1 = new Laptop("Komu", "ToPotrzebne", 1266, 8);
        Client client = new Client("Test", "Kowalski", "adam123", "password");
        repository.addEvent(new Event(laptop1, client));
        assertEquals(REPOSITORY_SIZE + 1, repository.getAllEvents().size());

    }

    @Test
    void getEvent() {
        assertEquals(event, repository.getEvent(event.getUuid()));
    }

    @Test
    void getEventsByUser() {
        assertEquals(CLIENT_EVENT_COUNT, repository.getEventsByUser(client.getUuid()).size());
    }

    @Test
    void getEventsByDevice() {
        assertEquals(LAPTOP_EVENT_COUNT, repository.getEventsByDevice(laptop.getUuid()).size());
        assertEquals(SMARTPHONE_EVENT_COUNT, repository.getEventsByDevice(smartphone.getUuid()).size());
    }

    @Test
    void getAllEvents() {
        assertEquals(REPOSITORY_SIZE, repository.getAllEvents().size());
    }

    @Test
    void deleteEvent() {
        repository.deleteEvent(event.getUuid());
        assertEquals(REPOSITORY_SIZE - 1, repository.getAllEvents().size());
    }

    @Test
    void endEvent() {
        assertNull(event.getReturnDate());
        repository.endEvent(event.getUuid());
        assertNotNull(event.getReturnDate());
    }
}