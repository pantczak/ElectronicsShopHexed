package pl.tks.rent_service.service.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.tks.rent_service.aggregates.DeviceRepositoryAdapter;
import pl.tks.rent_service.aggregates.EventRepositoryAdapter;
import pl.tks.rent_service.aggregates.UserRepositoryAdapter;
import pl.tks.rent_service.model_ent.repositories.DeviceEntRepository;
import pl.tks.rent_service.model_ent.repositories.EventEntRepository;
import pl.tks.rent_service.model_ent.repositories.UserEntRepository;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.model_domain.model.user.Administrator;
import pl.tks.rent_service.model_domain.model.user.Client;
import pl.tks.rent_service.model_domain.model.user.Employee;
import pl.tks.rent_service.service.DeviceServiceImpl;
import pl.tks.rent_service.service.EventServiceImpl;
import pl.tks.rent_service.service.UserServiceImpl;
import pl.tks.rent_service.service.interfaces.DeviceService;
import pl.tks.rent_service.service.interfaces.EventService;
import pl.tks.rent_service.service.interfaces.UserService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;


class EventServiceImplIT {

    private EventService service;
    private DeviceService deviceService;
    private UserService userService;

    private Laptop laptop = new Laptop("Macbook", "Pro", 1499, 32);
    private Smartphone smartphone = new Smartphone("Xiaomi", "Mi10", 450, 9.9);
    private Client client = new Client("Adam", "Adam", "asdsa", "asd");

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

        deviceService = new DeviceServiceImpl(deviceRepositoryAdapter, eventRepositoryAdapter);
        userService = new UserServiceImpl(userRepositoryAdapter);

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

        service = new EventServiceImpl(deviceRepositoryAdapter, eventRepositoryAdapter, userRepositoryAdapter);

    }

    @Test
    void borrowDevice() {
        //Brak jednego z argument??w
        assertFalse(service.borrowDevice(null,null));
        assertFalse(service.borrowDevice(laptop.getUuid(),null));
        assertFalse(service.borrowDevice(null,client.getUuid()));

        //Wypo??yczenie nieaktywnemu u??ytkownikowi

        userService.deactivateUser(client);
        assertFalse(service.borrowDevice(laptop.getUuid(), client.getUuid()));
        userService.activateUser(client);

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());
        assertTrue(laptop.isAvailable());

        //Wypo??yczenie niedozwolonemu u??ytkownikowi
        assertFalse(service.borrowDevice(laptop.getUuid(), employee.getUuid()));

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());
        assertTrue(laptop.isAvailable());

        //Poprawne wypo??yczenie
        assertTrue(service.borrowDevice(laptop.getUuid(), client.getUuid()));
        assertFalse(deviceService.getDevice(laptop.getUuid()).isAvailable());

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());
        assertFalse(laptop.isAvailable());

        //Wypo??yczenie niedost??pnego elementu
        assertFalse(service.borrowDevice(laptop.getUuid(), client.getUuid()));





    }

    @Test
    void returnDevice() {
        service.borrowDevice(laptop.getUuid(), client.getUuid());
        Event event = service.getEventsForDevice(laptop.getUuid()).get(0);
        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());


        //Zwr??cenie dla nieistniej??cego eventu
        assertFalse(service.returnDevice(null));


        //Poprawne zwr??cenie
        assertFalse(laptop.isAvailable());
        assertNull(event.getReturnDate());
        assertTrue(service.returnDevice(event));

        laptop = (Laptop) deviceService.getDevice(laptop.getUuid());

        assertTrue(laptop.isAvailable());

        event = service.getEvent(event.getUuid());

        assertNotNull(event.getReturnDate());

        //Zwr??cenie zwr??conego eventu
        assertFalse(service.returnDevice(event));

        //Zwr??cenie dla elementu nieznajduj??cego si?? w repozytorium
        assertFalse(service.returnDevice(new Event()));
    }

    @Test
    void deleteEvent() {

        service.borrowDevice(laptop.getUuid(), client.getUuid());
        Event event = service.getEventsForDevice(laptop.getUuid()).get(0);

        //Usuni??cie nieistniej??cego eventu

        assertFalse(service.deleteEvent(null));

        //Usuni??cie niezako??czonego eventu
        assertFalse(service.deleteEvent(event));
        assertNotNull(service.getEvent(event.getUuid()));

        //Poprawne usuni??cie
        service.returnDevice(event);
        event = service.getEventsForDevice(laptop.getUuid()).get(0);

        assertTrue(service.deleteEvent(event));
        assertNull(service.getEvent(event.getUuid()));

        //Usuni??cie elementu nieznajduj??cego si?? w repozytorium

        assertFalse(service.deleteEvent(event));

    }

    @Test
    void getEvent() {
        service.borrowDevice(laptop.getUuid(), client.getUuid());
        Event event = service.getEventsForDevice(laptop.getUuid()).get(0);

        //Dost??p do istniej??cego elementu

        Event loadedEvent = service.getEvent(event.getUuid());
        assertNotNull(loadedEvent);
        assertEquals(event.getUuid(),loadedEvent.getUuid());
        assertEquals(event.getDevice().getUuid(),loadedEvent.getDevice().getUuid());
        assertEquals(event.getClient().getUuid(),loadedEvent.getClient().getUuid());

        //Dost??p do nieistniej??cego elementu

        assertNull(service.getEvent(UUID.randomUUID()));
    }

    @Test
    void getEventsForClient() {

        //Dost??p do nieistniej??cego elementu
        assertTrue(service.getEventsForClient(client.getUuid()).isEmpty());


        //Dost??p do istniej??cego elementu

        service.borrowDevice(laptop.getUuid(), client.getUuid());

        List<Event> list = service.getEventsForClient(client.getUuid());
        assertNotNull(list);
        assertEquals(1,list.size());
        assertEquals(client.getUuid(),list.get(0).getClient().getUuid());
        assertEquals(laptop.getUuid(),list.get(0).getDevice().getUuid());


    }

    @Test
    void getEventsForDevice() {

        //Dost??p do nieistniej??cego elementu
        assertTrue(service.getEventsForDevice(laptop.getUuid()).isEmpty());


        //Dost??p do istniej??cego elementu

        service.borrowDevice(laptop.getUuid(), client.getUuid());

        List<Event> list = service.getEventsForDevice(laptop.getUuid());
        assertNotNull(list);
        assertEquals(1,list.size());
        assertEquals(client.getUuid(),list.get(0).getClient().getUuid());
        assertEquals(laptop.getUuid(),list.get(0).getDevice().getUuid());
    }

    @Test
    void getAllEvents() {

        //Brak element??w w repozytorium
        assertTrue(service.getAllEvents().isEmpty());


        //Dost??p do istniej??cego elementu

        service.borrowDevice(laptop.getUuid(), client.getUuid());

        List<Event> list = service.getAllEvents();
        assertNotNull(list);
        assertEquals(1,list.size());
        assertEquals(client.getUuid(),list.get(0).getClient().getUuid());
        assertEquals(laptop.getUuid(),list.get(0).getDevice().getUuid());
    }
}