package pl.tks.rent_service.model_ent.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.tks.rent_service.model_ent.model.EventEnt;
import pl.tks.rent_service.model_ent.model.resource.LaptopEnt;
import pl.tks.rent_service.model_ent.model.resource.SmartphoneEnt;
import pl.tks.rent_service.model_ent.model.user.ClientEnt;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventEntRepositoryTest {

    private final static int REPOSITORY_SIZE = 3;
    private final static int CLIENT_EVENT_COUNT = 3;
    private final static int SMARTPHONE_EVENT_COUNT = 1;
    private final static int LAPTOP_EVENT_COUNT = 2;

    private EventEntRepository repository;
    private final ClientEnt client = new ClientEnt("Adam", "Kowalski", "adam123", "password");
    private final SmartphoneEnt smartphone = new SmartphoneEnt("Xiaomi", "Mi10", 400, 6.6);
    private final LaptopEnt laptop = new LaptopEnt("Samsung", "L20", 1266, 8);

    private final EventEnt event = new EventEnt(smartphone, client, new Date());
    private final EventEnt event1 = new EventEnt(laptop, client, new Date());
    private final EventEnt event2 = new EventEnt(laptop, client, new Date());
    ;

    @BeforeAll
    void initEntitiesUUID() {
        client.setUuid(UUID.randomUUID());
        laptop.setUuid(UUID.randomUUID());
        smartphone.setUuid(UUID.randomUUID());
    }

    @BeforeEach
    void initialize() {

        repository = new EventEntRepository();
        assertTrue(repository.getAllEvents().isEmpty());
        repository.addEvent(event);
        repository.addEvent(event1);
        repository.addEvent(event2);
    }


    @Test
    void addEvent() {
        LaptopEnt laptop1 = new LaptopEnt("Komu", "ToPotrzebne", 1266, 8);
        ClientEnt client = new ClientEnt("Test", "Kowalski", "adam123", "password");
        repository.addEvent(new EventEnt(laptop1, client));
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