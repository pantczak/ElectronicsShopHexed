package pl.tks.rent_service.model_domain.model.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Smartphone extends Device {
    private double batteryLifetime;

    public Smartphone(String brand, String model, int weightInGrams, double batteryLifetime) {
        super(brand, model, true, weightInGrams);
        this.batteryLifetime = batteryLifetime;
    }
}
