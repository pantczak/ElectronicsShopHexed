package pl.tks.rent_service.model_domain.model.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Laptop extends Device {
    private int memoryInGb;

    public Laptop(String brand, String model, int weightInGrams, int memoryInGb) {
        super(brand, model, true, weightInGrams);
        this.memoryInGb = memoryInGb;
    }
}
