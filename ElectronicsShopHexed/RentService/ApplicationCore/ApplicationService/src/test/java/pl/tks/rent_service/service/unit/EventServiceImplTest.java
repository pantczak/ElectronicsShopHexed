package pl.tks.rent_service.service.unit;

import pl.tks.rent_service.service.EventServiceImpl;
import pl.tks.rent_service.infrastructure.DevicePort;
import pl.tks.rent_service.infrastructure.EventPort;
import pl.tks.rent_service.infrastructure.UserPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl service;

    @Mock(name = "eventPort")
    private EventPort eventPort;

    @Mock(name = "devicePort")
    private DevicePort devicePort;

    @Mock(name = "userPort")
    private UserPort userPort;

    @Captor
    private ArgumentCaptor<Event> eventArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    private final Laptop testDevice = new Laptop("Macbook", "Pro", 1234, 32);
    private final Smartphone testDevice1 = new Smartphone("Xiaomi", "Mi10", 600, 5.5);
    private final Client testClient = new Client("Adam", "Adam", "asdsa", "asd");
    private final Event testEvent = new Event(testDevice, testClient);
    private final Event testEvent1 = new Event(testDevice1, testClient);
    private final List<Event> eventList = new ArrayList<>(List.of(testEvent1, testEvent));


    @Test
    void borrowDevice() {
        //given testDevice, testClient
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(devicePort.getDevice(any(UUID.class))).willReturn(laptop);
        given(userPort.getUser(any(UUID.class))).willReturn(client);
        //when
        service.borrowDevice(laptop.getUuid(), client.getUuid());
        verify(eventPort).addEvent(eventArgumentCaptor.capture());
        //then
        then(eventPort).should().addEvent(eventArgumentCaptor.getValue());
        assertEquals(laptop, eventArgumentCaptor.getValue().getDevice());
        assertEquals(client, eventArgumentCaptor.getValue().getClient());
    }

    @Test
    void borrowDeviceWithDate() {
        //given
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(devicePort.getDevice(any(UUID.class))).willReturn(laptop);
        given(userPort.getUser(any(UUID.class))).willReturn(client);
        //when
        service.borrowDevice(laptop.getUuid(), client.getUuid(), new Date());
        verify(eventPort).addEvent(eventArgumentCaptor.capture());
        //then
        then(eventPort).should().addEvent(eventArgumentCaptor.getValue());
        then(eventPort).shouldHaveNoMoreInteractions();
        assertEquals(laptop, eventArgumentCaptor.getValue().getDevice());
        assertEquals(client, eventArgumentCaptor.getValue().getClient());
    }

    @Test
    void returnDevice() {
        //given
        Event event = testEvent;
        event.setUuid(UUID.randomUUID());
        given(eventPort.getEvent(any(UUID.class))).willReturn(event);
        //when
        doAnswer(invocation -> {
            event.setReturnDate(new Date());
            return invocation;
        }).when(eventPort).endEvent(any(UUID.class));

        service.returnDevice(event);
        verify(eventPort).endEvent(uuidArgumentCaptor.capture());
        //then
        then(eventPort).should().endEvent(event.getUuid());
        then(eventPort).shouldHaveNoMoreInteractions();
        assertEquals(event.getUuid(), uuidArgumentCaptor.getValue());
        assertNotNull(event.getReturnDate());
    }

    @Test
    void deleteEvent() {
        //given
        Event event = testEvent;
        event.setUuid(UUID.randomUUID());
        event.setReturnDate(new Date());
        given(eventPort.getEvent(any(UUID.class))).willReturn(event);
        //when
        service.deleteEvent(event);
        //then
        then(eventPort).should().deleteEvent(event.getUuid());
        then(eventPort).shouldHaveNoMoreInteractions();
    }

    @Test
    void getEvent() {
        //given
        Event event = testEvent;
        event.setUuid(UUID.randomUUID());
        given(eventPort.getEvent(any(UUID.class))).willReturn(event);
        //when
        service.getEvent(event.getUuid());
        //then
        then(eventPort).should().getEvent(event.getUuid());
        then(eventPort).shouldHaveNoMoreInteractions();
    }

    @Test
    void getEventsForClient() {
        Client client = testClient;
        client.setUuid(UUID.randomUUID());
        given(eventPort.getEventsByUser(any(UUID.class))).willReturn(eventList.stream()
                .filter(event -> event.getClient().equals(client))
                .collect(Collectors.toList()));
        //when
        List<Event> list = service.getEventsForClient(client.getUuid());
        //then
        then(eventPort).should().getEventsByUser(client.getUuid());
        then(eventPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getEventsForDevice() {
        Laptop laptop = testDevice;
        laptop.setUuid(UUID.randomUUID());
        given(eventPort.getEventsByDevice(any(UUID.class))).willReturn(eventList.stream()
                .filter(event -> event.getDevice().equals(laptop))
                .collect(Collectors.toList()));
        //when
        List<Event> list = service.getEventsForDevice(laptop.getUuid());
        //then
        then(eventPort).should().getEventsByDevice(laptop.getUuid());
        then(eventPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }

    @Test
    void getAllEvents() {
        //given
        given(eventPort.getAllEvents()).willReturn(eventList);
        //when
        List<Event> list = service.getAllEvents();
        //then
        then(eventPort).should().getAllEvents();
        then(eventPort).shouldHaveNoMoreInteractions();
        assertFalse(list.isEmpty());
    }
}