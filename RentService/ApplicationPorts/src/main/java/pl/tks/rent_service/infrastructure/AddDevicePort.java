package pl.tks.rent_service.infrastructure;


import pl.tks.rent_service.model_domain.model.resource.Device;

public interface AddDevicePort {
    boolean addDevice(Device device);
}
