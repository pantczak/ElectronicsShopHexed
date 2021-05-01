package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.resource.Device;
import pl.java.tks.model_domain.model.resource.Laptop;
import pl.java.tks.model_domain.model.resource.Smartphone;

import java.util.List;
import java.util.UUID;

public interface GetDevicePort<T1, T2, T3> {
    T1 getDevice(UUID uuid);

    List<T1> getAllDevices();

    List<T2> getAllLaptops();

    List<T3> getAllSmartphones();
}
