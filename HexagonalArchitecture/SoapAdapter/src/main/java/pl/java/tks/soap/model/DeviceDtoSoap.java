package pl.java.tks.soap.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deviceDtoSoap", propOrder = {
        "uuid",
        "brand",
        "model",
        "isAvailable",
        "weightInGrams",
        "memoryInGb",
        "batteryLifetime"
})
public class DeviceDtoSoap {
    private String uuid;
    private String brand;
    private String model;
    private boolean isAvailable;
    private int weightInGrams;
    private int memoryInGb;
    private double batteryLifetime;
}
