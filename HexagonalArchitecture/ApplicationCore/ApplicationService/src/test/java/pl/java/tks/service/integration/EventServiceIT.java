package pl.java.tks.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.java.tks.aggregates.DeviceRepositoryAdapter;
import pl.java.tks.aggregates.EventRepositoryAdapter;
import pl.java.tks.aggregates.UserRepositoryAdapter;
import pl.java.tks.model_domain.model.Event;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.model_domain.model.user.Administrator;
import pl.java.tks.model_domain.model.user.Client;
import pl.java.tks.model_domain.model.user.Employee;
import pl.java.tks.model_ent.repositories.DeviceEntRepository;
import pl.java.tks.model_ent.repositories.EventEntRepository;
import pl.java.tks.model_ent.repositories.UserEntRepository;
import pl.java.tks.service.DeviceService;
import pl.java.tks.service.EventService;
import pl.java.tks.service.UserService;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;


class EventServiceIT {

    private EventService service;
    private DeviceService deviceService;
    private UserService userService;

    private Laptop laptop = new Laptop("Macbook", "Pro", 1499, 32);
    private Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 450, 9.9);
    private Client client = new Client("Adam", "Adam", "asdsa", "asd", 14);

    private Employee employee = new Employee("Agfgdfdam", "Addfgam", "asdfgdsa", "asd");

    private Administrator administrator = new Administrator("Adam", "Adam", "adfgdfgsdsa", "asd");

    @BeforeEach
    public void initialize() {
        laptop.setUuid(UUID.randomUUID());
        smartphone.setUuid(UUID.randomUUID());

        client.setUuid(UUID.randomUUID());
        employee.setUuid(UUID.randomUUID());
        administrator.setUuid(UUID.randomUUID());

        DeviceRepositoryAdapter deviceRepositoryAdapter = new DeviceRepositoryAdapter(new DeviceEntRepository());
        EventRepositoryAdapter eventRepositoryAdapter = new EventRepositoryAdapter(new EventEntRepository());
        UserRepositoryAdapter userRepositoryAdapter = new UserRepositoryAdapter(new UserEntRepository());

        deviceService = new DeviceService(deviceRepositoryAdapter, eventRepositoryAdapter);
        userService = new UserService(userRepositoryAdapter);

        userService.addClient(client);
        client = userService.getAllClients().get(0);

        userService.addEmployee(employee);
        employee = (Employee) userService.getAllEmployees().get(0);

        userService.addAdministrator(administrator);
        administrator = (Administrator) userService.getAllAdministrators().get(0);



        deviceService.addLaptop(laptop);
        deviceService.addSmartphone(smartphone);
        laptop = deviceService.getAllLaptops().get(0);
        smartphone = deviceService.getAllSmartphones().get(0);

        service = new EventService(deviceRepositoryAdapter, eventRepositoryAdapter, userRepositoryAdapter);

    }

    @Test
    void borrowDevice() {
        //Brak jednego z argumentów
        assertFalse(service.borrowDevice(null,null));
        assertFalse(service.borrowDevice(laptop.getUuid(),null));
        assertFalse(service.borrowDevice(null,client.getUuid()));

        //Wypożyczenie nieaktywnemu użytkownikowi

        userService.deactivateUser(client);
        assertFalse(service.borrowDevice(laptop.getUuid(), client.getUuid()));
        userService.activateUser(client);

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());
        assertTrue(laptop.isAvailable());

        //Wypożyczenie niedozwolonemu użytkownikowi
        assertFalse(service.borrowDevice(laptop.getUuid(), employee.getUuid()));

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());
        assertTrue(laptop.isAvailable());

        //Poprawne wypożyczenie
        assertTrue(service.borrowDevice(laptop.getUuid(), client.getUuid()));
        assertFalse(deviceService.getDevice(laptop.getUuid()).isAvailable());

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());
        assertFalse(laptop.isAvailable());

        //Wypożyczenie niedostępnego elementu
        assertFalse(service.borrowDevice(laptop.getUuid(), client.getUuid()));





    }

    @Test
    void returnDevice() {
        service.borrowDevice(laptop.getUuid(), client.getUuid());
        Event event = service.getEventsForDevice(laptop.getUuid()).get(0);
        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());


        //Zwrócenie dla nieistniejącego eventu
        assertFalse(service.returnDevice(null));


        //Poprawne zwrócenie
        assertFalse(laptop.isAvailable());
        assertNull(event.getReturnDate());
        assertTrue(service.returnDevice(event));

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());

        assertTrue(laptop.isAvailable());

        event = service.getEvent(event.getUuid());

        assertNotNull(event.getReturnDate());

        //Zwrócenie zwróconego eventu
        assertFalse(service.returnDevice(event));

        //Zwrócenie dla elementu nieznajdującego się w repozytorium
        assertFalse(service.returnDevice(new Event()));
    }

    @Test
    void deleteEvent() {

        service.borrowDevice(laptop.getUuid(), client.getUuid());
        Event event = service.getEventsForDevice(laptop.getUuid()).get(0);

        //Usunięcie nieistniejącego eventu

        assertFalse(service.deleteEvent(null));

        //Usunięcie niezakończonego eventu
        assertFalse(service.deleteEvent(event));
        assertNotNull(service.getEvent(event.getUuid()));

        //Poprawne usunięcie
        service.returnDevice(event);
        event = service.getEventsForDevice(laptop.getUuid()).get(0);

        assertTrue(service.deleteEvent(event));
        assertNull(service.getEvent(event.getUuid()));

        //Usunięcie elementu nieznajdującego się w repozytorium

        assertFalse(service.deleteEvent(event));

    }

    @Test
    void getEvent() {
        service.borrowDevice(laptop.getUuid(), client.getUuid());
        Event event = service.getEventsForDevice(laptop.getUuid()).get(0);

        //Dostęp do istniejącego elementu

        Event loadedEvent = service.getEvent(event.getUuid());
        assertNotNull(loadedEvent);
        assertEquals(event.getUuid(),loadedEvent.getUuid());
        assertEquals(event.getDevice().getUuid(),loadedEvent.getDevice().getUuid());
        assertEquals(event.getClient().getUuid(),loadedEvent.getClient().getUuid());

        //Dostęp do nieistniejącego elementu

        assertNull(service.getEvent(UUID.randomUUID()));
    }

    @Test
    void getEventsForClient() {

        //Dostęp do nieistniejącego elementu
        assertTrue(service.getEventsForClient(client.getUuid()).isEmpty());


        //Dostęp do istniejącego elementu

        service.borrowDevice(laptop.getUuid(), client.getUuid());

        List<Event> list = service.getEventsForClient(client.getUuid());
        assertNotNull(list);
        assertEquals(1,list.size());
        assertEquals(client.getUuid(),list.get(0).getClient().getUuid());
        assertEquals(laptop.getUuid(),list.get(0).getDevice().getUuid());


    }

    @Test
    void getEventsForDevice() {

        //Dostęp do nieistniejącego elementu
        assertTrue(service.getEventsForDevice(laptop.getUuid()).isEmpty());


        //Dostęp do istniejącego elementu

        service.borrowDevice(laptop.getUuid(), client.getUuid());

        List<Event> list = service.getEventsForDevice(laptop.getUuid());
        assertNotNull(list);
        assertEquals(1,list.size());
        assertEquals(client.getUuid(),list.get(0).getClient().getUuid());
        assertEquals(laptop.getUuid(),list.get(0).getDevice().getUuid());
    }

    @Test
    void getAllEvents() {

        //Brak elementów w repozytorium
        assertTrue(service.getAllEvents().isEmpty());


        //Dostęp do istniejącego elementu

        service.borrowDevice(laptop.getUuid(), client.getUuid());

        List<Event> list = service.getAllEvents();
        assertNotNull(list);
        assertEquals(1,list.size());
        assertEquals(client.getUuid(),list.get(0).getClient().getUuid());
        assertEquals(laptop.getUuid(),list.get(0).getDevice().getUuid());
    }
}