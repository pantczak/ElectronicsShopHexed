package pl.tks.rent_service.user_interfaces;



import pl.tks.rent_service.model_domain.model.resource.Device;

import java.util.UUID;

public interface UpdateDevicePort {
    void updateDevice(UUID uuid, Device newDevice);
}
