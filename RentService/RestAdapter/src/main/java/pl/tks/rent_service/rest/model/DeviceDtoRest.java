package pl.tks.rent_service.rest.model;

import lombok.Data;

@Data
public class DeviceDtoRest {
    private String uuid;
    private String brand;
    private String model;
    private boolean isAvailable;
    private int weightInGrams;
    private int memoryInGb;
    private double batteryLifetime;
}
