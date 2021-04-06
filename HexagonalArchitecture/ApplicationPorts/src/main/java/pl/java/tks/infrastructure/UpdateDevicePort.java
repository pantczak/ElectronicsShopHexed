package pl.java.tks.infrastructure;

import pl.java.tks.model_domain.model.resource.Device;

import java.util.UUID;

public interface UpdateDevicePort {
    void updateDevice(UUID uuid, Device newDevice);
}
