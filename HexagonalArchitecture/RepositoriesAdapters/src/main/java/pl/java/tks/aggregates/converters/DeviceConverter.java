package pl.java.tks.aggregates.converters;

import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;
import pl.java.tks.model_ent.model.resource.DeviceEnt;
import pl.java.tks.model_ent.model.resource.LaptopEnt;
import pl.java.tks.model_ent.model.resource.SmartphoneEnt;

public class DeviceConverter {

    private DeviceConverter() {
    }

    public static DeviceEnt convertDeviceToEnt(Device device) {
        DeviceEnt result = null;
        if (device instanceof Smartphone) {
            SmartphoneEnt smartphoneEnt = new SmartphoneEnt(device.getBrand(), device.getModel(), device.getWeightInGrams(), ((Smartphone) device).getBatteryLifetime());
            smartphoneEnt.setAvailable(device.isAvailable());
            smartphoneEnt.setUuid(device.getUuid());
            result = smartphoneEnt;
        }
        if (device instanceof Laptop) {
            LaptopEnt laptopEnt = new LaptopEnt(device.getBrand(), device.getModel(), device.getWeightInGrams(), ((Laptop) device).getMemoryInGb());
            laptopEnt.setAvailable(device.isAvailable());
            laptopEnt.setUuid(device.getUuid());
            result = laptopEnt;
        }
        return result;
    }

    public static Device convertEntToDevice(DeviceEnt device) {
        Device result = null;
        if (device instanceof SmartphoneEnt) {
            Smartphone smartphone = new Smartphone(device.getBrand(), device.getModel(), device.getWeightInGrams(), ((SmartphoneEnt) device).getBatteryLifetime());
            smartphone.setAvailable(device.isAvailable());
            smartphone.setUuid(device.getUuid());
            result = smartphone;
        }
        if (device instanceof LaptopEnt) {
            Laptop laptop = new Laptop(device.getBrand(), device.getModel(), device.getWeightInGrams(), ((LaptopEnt) device).getMemoryInGb());
            laptop.setAvailable(device.isAvailable());
            laptop.setUuid(device.getUuid());
            result = laptop;
        }
        return result;

    }
}
