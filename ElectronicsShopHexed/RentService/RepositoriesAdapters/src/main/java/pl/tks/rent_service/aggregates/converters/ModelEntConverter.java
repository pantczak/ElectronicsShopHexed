package pl.tks.rent_service.aggregates.converters;


import pl.tks.rent_service.model_ent.model.EventEnt;
import pl.tks.rent_service.model_ent.model.resource.DeviceEnt;
import pl.tks.rent_service.model_ent.model.resource.LaptopEnt;
import pl.tks.rent_service.model_ent.model.resource.SmartphoneEnt;
import pl.tks.rent_service.model_ent.model.user.ClientEnt;
import pl.tks.rent_service.model_domain.model.Event;
import pl.tks.rent_service.model_domain.model.resource.Device;
import pl.tks.rent_service.model_domain.model.resource.Laptop;
import pl.tks.rent_service.model_domain.model.resource.Smartphone;
import pl.tks.rent_service.model_domain.model.user.Client;

public class ModelEntConverter {

    public DeviceEnt convertDeviceToEnt(Device device) {
        if (device.getClass() == Smartphone.class) {
            return convertSmartphoneToEnt((Smartphone) device);
        } else if (device.getClass() == Laptop.class) {
            return convertLaptopToEnt((Laptop) device);
        }
        return null;
    }

    public Device convertEntToDevice(DeviceEnt device) {
        if (device.getClass() == SmartphoneEnt.class) {
            return convertEntToSmartphone((SmartphoneEnt) device);
        } else if (device.getClass() == LaptopEnt.class) {
            return convertEntToLaptop((LaptopEnt) device);
        }
        return null;
    }

    public Smartphone convertEntToSmartphone(SmartphoneEnt smartphoneEnt) {
        return new Smartphone(smartphoneEnt.getBrand(), smartphoneEnt.getBrand(), smartphoneEnt.getWeightInGrams(), smartphoneEnt.getBatteryLifetime());
    }

    public SmartphoneEnt convertSmartphoneToEnt(Smartphone smartphone) {
        return new SmartphoneEnt(smartphone.getBrand(), smartphone.getModel(), smartphone.getWeightInGrams(), smartphone.getBatteryLifetime());
    }

    public Laptop convertEntToLaptop(LaptopEnt laptopEnt) {
        return new Laptop(laptopEnt.getBrand(), laptopEnt.getModel(), laptopEnt.getWeightInGrams(), laptopEnt.getMemoryInGb());
    }

    public LaptopEnt convertLaptopToEnt(Laptop laptop) {
        return new LaptopEnt(laptop.getBrand(), laptop.getModel(), laptop.getWeightInGrams(), laptop.getMemoryInGb());
    }

    public Client convertEntToClient(ClientEnt clientEnt) {
        return new Client(clientEnt.getName(), clientEnt.getLastName(), clientEnt.getLogin(), clientEnt.getPassword());
    }

    public ClientEnt convertClientToEnt(Client client) {
        return new ClientEnt(client.getName(), client.getLastName(), client.getLogin(), client.getPassword());
    }

    public EventEnt convertEventToEnt(Event event) {
        EventEnt eventEnt = new EventEnt(convertDeviceToEnt(event.getDevice()), convertClientToEnt(event.getClient()), event.getBorrowDate());
        if (event.getReturnDate() != null) {
            eventEnt.setReturnDate(event.getReturnDate());
        }
        return eventEnt;
    }

    public Event convertEntToEvent(EventEnt eventEnt) {
        Event event = new Event(convertEntToDevice(eventEnt.getDevice()), convertEntToClient(eventEnt.getClient()), eventEnt.getBorrowDate());
        if (eventEnt.getReturnDate() != null) {
            event.setReturnDate(eventEnt.getReturnDate());
        }
        return event;
    }
}
