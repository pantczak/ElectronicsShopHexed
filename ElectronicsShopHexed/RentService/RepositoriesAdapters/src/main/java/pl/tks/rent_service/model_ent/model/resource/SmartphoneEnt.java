package pl.tks.rent_service.model_ent.model.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SmartphoneEnt extends DeviceEnt {
    private double batteryLifetime;

    public SmartphoneEnt(String brand, String model, int weightInGrams, double batteryLifetime) {
        super(brand, model, true, weightInGrams);
        this.batteryLifetime = batteryLifetime;
    }
}
