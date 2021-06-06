package pl.tks.rent_service.user_interfaces;


import java.util.List;
import java.util.UUID;

public interface GetDevicePort<T1, T2, T3> {
    T1 getDevice(UUID uuid);

    List<T1> getAllDevices();

    List<T2> getAllLaptops();

    List<T3> getAllSmartphones();
}
