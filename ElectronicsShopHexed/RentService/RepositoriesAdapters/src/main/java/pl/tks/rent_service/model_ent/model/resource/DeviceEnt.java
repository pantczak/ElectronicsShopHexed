package pl.tks.rent_service.model_ent.model.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.tks.rent_service.model_ent.model.EntityEnt;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class DeviceEnt extends EntityEnt {
    private String brand;
    private String model;
    private boolean isAvailable;
    private int weightInGrams;
}
