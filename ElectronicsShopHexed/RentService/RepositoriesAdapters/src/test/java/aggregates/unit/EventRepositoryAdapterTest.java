package aggregates.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.tks.rent_service.aggregates.EventRepositoryAdapter;
import pl.tks.rent_service.aggregates.converters.EventConverter;

import pl.tks.rent_service.model_ent.model.EventEnt;
import pl.tks.rent_service.model_ent.repositories.EventEntRepository;
import pl.tks.rent_service.model_domain.model.Entity;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.model_domain.model.user.Client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
class EventRepositoryAdapterTest {

    @InjectMocks
    private EventRepositoryAdapter adapter;

    @Mock(name = "repository")
    private EventEntRepository repository;

    @Captor
    private ArgumentCaptor<EventEnt> eventEntArgumentCaptor;

    private final Laptop testDevice = new Laptop("Macbook", "Pro", 1234, 32);
    private final Smartphone testDevice1 = new Smartphone("Xiaomi", "Mi10", 600, 5.5);
    private final Client testClient = new Client("Adam", "Adam", "asdsa", "asd");
    private final Event testEvent = new Event(testDevice, testClient);
    private final Event testEvent1 = new Event(testDevice1, testClient);
    private final List<Event> eventList = new ArrayList<>(List.of(testEvent1, testEvent));

    @Test
    void addEvent() {
        //given
            //testEvent
        //when
        adapter.addEvent(testEvent);
        Mockito.verify(repository).addEvent(eventEntArgumentCaptor.capture());
        //then
        then(repository).should().addEvent(eventEntArgumentCaptor.getValue());
        then(repository).shouldHaveNoMoreInteractions();

    }

    @Test
    void deleteEvent() {
        //given
        Event event = testEvent;
        event.setUuid(UUID.randomUUID());
        //when
        adapter.deleteEvent(event.getUuid());
        //then
        then(repository).should().deleteEvent(event.getUuid());
        then(repository).shouldHaveNoMoreInteractions();

    }

    @Test
    void endEvent() {
        //given
        Event event = testEvent;
        event.setUuid(UUID.randomUUID());
        assertNull(event.getReturnDate());
        //when
        doAnswer(invocation -> {
            event.setReturnDate(new Date());
            return invocation;
        }).when(repository).endEvent(any(UUID.class));
        adapter.endEvent(event.getUuid());
        //then
        then(repository).should().endEvent(event.getUuid());
        then(repository).shouldHaveNoMoreInteractions();
        assertNotNull(event.getReturnDate());
    }

    @Test
    void getEvent() {
        //given
        Event event = testEvent;
        event.setUuid(UUID.randomUUID());
        given(repository.getEvent(any(UUID.class))).willReturn(EventConverter.convertEventToEnt(event));
        //when
        Entity ent = adapter.getEvent(event.getUuid());
        //then
        then(repository).should().getEvent(event.getUuid());
        then(repository).shouldHaveNoMoreInteractions();
        assertNotNull(ent);
    }

    @Test
    void getEventsByUser() {
        //given
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(repository.getEventsByUser(any(UUID.class))).willReturn(eventList.stream()
                .filter(event -> event.getClient().equals(client)).map(EventConverter::convertEventToEnt)
                .collect(Collectors.toList()));
        //when
        List<Event> events = adapter.getEventsByUser(client.getUuid());
        //then
        then(repository).should().getEventsByUser(client.getUuid());
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(events.isEmpty());
    }

    @Test
    void getEventsByDevice() {
        //given
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        given(repository.getEventsByDevice(any(UUID.class))).willReturn(eventList.stream()
                .filter(event -> event.getDevice().equals(laptop)).map(EventConverter::convertEventToEnt)
                .collect(Collectors.toList()));
        //when
        List<Event> events = adapter.getEventsByDevice(laptop.getUuid());
        //then
        then(repository).should().getEventsByDevice(laptop.getUuid());
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(events.isEmpty());
    }

    @Test
    void getAllEvents() {
        //given
        given(repository.getAllEvents()).willReturn(eventList.stream().map(EventConverter::convertEventToEnt)
                .collect(Collectors.toList()));
        //when
        List<Event> list = adapter.getAllEvents();
        //then
        then(repository).should().getAllEvents();
        then(repository).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }
}