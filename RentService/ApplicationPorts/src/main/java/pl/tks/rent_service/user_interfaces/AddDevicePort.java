package pl.tks.rent_service.user_interfaces;



public interface AddDevicePort<T1> {
    boolean addSmartphone(T1 smartphone);

    boolean addLaptop(T1 laptop);
}
