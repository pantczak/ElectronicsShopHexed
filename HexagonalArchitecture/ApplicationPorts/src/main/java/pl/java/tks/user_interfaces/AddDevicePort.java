package pl.java.tks.user_interfaces;

import pl.java.tks.model_domain.model.resource.Device;

public interface AddDevicePort<T1> {
    boolean addSmartphone(T1 smartphone);

    boolean addLaptop(T1 laptop);
}
