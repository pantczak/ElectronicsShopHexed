package aggregates.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.java.tks.aggregates.EventRepositoryAdapter;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_ent.model.EventEnt;
import pl.java.tks.model_ent.model.resource.LaptopEnt;
import pl.java.tks.model_ent.model.resource.SmartphoneEnt;
import pl.java.tks.model_ent.model.user.ClientEnt;
import pl.java.tks.model_ent.repositories.EventEntRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventRepositoryAdapterIT {

    private EventRepositoryAdapter adapter;
    private EventEntRepository repository;
    //test data
    private final Laptop testLaptop = new Laptop("Macbook", "Pro", 1234, 32);
    private final Client testClient = new Client("TestName", "TestSurname", "TsTLgn", "TestPasswoRD", 14);
    private final Event testEvent = new Event(testLaptop, testClient);
    //initial data
    private static final int INITIAL_REPOSITORY_SIZE = 2;
    private final ClientEnt initialClient = new ClientEnt("Adam", "Ktoto", "lgon123", "passwd123", 14);
    private final SmartphoneEnt initialSmartphone = new SmartphoneEnt("Xiaomi", "Mi10", 600, 5.5);
    private final LaptopEnt initialLaptop = new LaptopEnt("Hp", "ProBook", 1111, 8);
    private final EventEnt initialEvent = new EventEnt(initialLaptop, initialClient);
    private final EventEnt initialEvent1 = new EventEnt(initialSmartphone, initialClient);

    @BeforeAll
    void initRepository() {
        initialClient.setUuid(UUID.randomUUID());
        initialLaptop.setUuid(UUID.randomUUID());
        initialSmartphone.setUuid(UUID.randomUUID());
        initialEvent.setUuid(UUID.randomUUID());
        initialEvent1.setUuid(UUID.randomUUID());
    }

    @BeforeEach
    void init() {
        repository = new EventEntRepository();
        repository.addEvent(initialEvent);
        repository.addEvent(initialEvent1);
        //verify precondition
        assertEquals(INITIAL_REPOSITORY_SIZE, repository.getAllEvents().size());
        adapter = new EventRepositoryAdapter(repository);
    }

    @Test
    void addEvent() {
        adapter.addEvent(testEvent);
        assertEquals(INITIAL_REPOSITORY_SIZE + 1, repository.getAllEvents().size());
    }

    @Test
    void deleteEvent() {
        adapter.deleteEvent(initialEvent1.getUuid());
        assertEquals(INITIAL_REPOSITORY_SIZE - 1, repository.getAllEvents().size());
    }

    @Test
    void endEvent() {
        assertNull(initialEvent.getReturnDate());
        adapter.endEvent(initialEvent.getUuid());
        assertNotNull(initialEvent.getReturnDate());
    }

    @Test
    void getEvent() {
        Event event = adapter.getEvent(initialEvent.getUuid());
        assertNotNull(event);
        assertEquals(initialEvent.getUuid(), event.getUuid());
        assertEquals(initialEvent.getDevice().getModel(), event.getDevice().getModel());
        assertEquals(initialEvent.getBorrowDate(), event.getBorrowDate());
        assertEquals(initialEvent.getClient().getName(), event.getClient().getName());
        assertEquals(initialEvent.getReturnDate(), event.getReturnDate());
    }

    @Test
    void getEventsByUser() {
        List<Event> list = adapter.getEventsByUser(initialClient.getUuid());
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
    }

    @Test
    void getEventsByDevice() {
        List<Event> list = adapter.getEventsByDevice(initialLaptop.getUuid());
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void getAllEvents() {
        List<Event> list = adapter.getAllEvents();
        assertFalse(list.isEmpty());
        assertEquals(INITIAL_REPOSITORY_SIZE, list.size());
    }
}