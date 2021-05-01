package pl.java.tks.rest.aggregates.mappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.rest.model.EventDtoRest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EventMapperTest {

    private final Client client = new Client("Adam", "Adamczyk", "adam123", "password", 19);

    private final Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 1235, 21.3);

    Laptop laptop = new Laptop("Xiaomi", "Mi10", 1235, 21);

    @BeforeAll
    void init() {
        client.setUuid(UUID.randomUUID());
        smartphone.setUuid(UUID.randomUUID());
        laptop.setUuid(UUID.randomUUID());
    }

    @Test
    void eventToEventDto() {
        //given
        Event event = new Event(smartphone, client);
        event.setUuid(UUID.randomUUID());

        //when
        EventDtoRest eventDtoSoap = EventMapper.INSTANCE.eventToEventDto(event);

        //then
        assertNotNull(eventDtoSoap);
        assertEquals(event.getUuid().toString(), eventDtoSoap.getUuid());
        //client
        assertEquals(event.getClient().getUuid().toString(), eventDtoSoap.getClient().getUuid() );
        assertEquals(event.getClient().getName(), eventDtoSoap.getClient().getName());
        assertEquals(event.getClient().getLastName(), eventDtoSoap.getClient().getLastName());
        assertEquals(event.getClient().getLogin(), eventDtoSoap.getClient().getLogin());
        assertEquals(event.getClient().getPassword(), eventDtoSoap.getClient().getPassword());
        assertEquals(event.getClient().getAge(), eventDtoSoap.getClient().getAge());
        //device
        assertEquals(event.getDevice().getUuid().toString(), eventDtoSoap.getDevice().getUuid());
        assertEquals(0, eventDtoSoap.getDevice().getMemoryInGb());
        assertEquals(event.getDevice().getBrand(), eventDtoSoap.getDevice().getBrand());
        assertEquals(event.getDevice().getModel(), eventDtoSoap.getDevice().getModel());
        assertEquals(event.getDevice().getWeightInGrams(), eventDtoSoap.getDevice().getWeightInGrams());

    }

    @Test
    void toEvent() {
        //given
        //given
        Event event = new Event(smartphone, client);
        event.setUuid(UUID.randomUUID());
        EventDtoRest eventDtoSoap = EventMapper.INSTANCE.eventToEventDto(event);
        //when
        Event convertedFromDto = EventMapper.INSTANCE.toEvent(eventDtoSoap);
        //then
        assertEquals(event, convertedFromDto);
    }
}