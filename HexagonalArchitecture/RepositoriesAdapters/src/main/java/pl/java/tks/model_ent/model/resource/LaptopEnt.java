package pl.java.tks.model_ent.model.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class LaptopEnt extends DeviceEnt {
    private int memoryInGb;

    public LaptopEnt(String brand, String model, int weightInGrams, int memoryInGb) {
        super(brand, model, true, weightInGrams);
        this.memoryInGb = memoryInGb;
    }
}
