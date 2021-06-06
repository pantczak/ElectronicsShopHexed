package pl.tks.rent_service.model_domain.model.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.tks.rent_service.model_domain.model.Entity;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class Device extends Entity {
    private String brand;
    private String model;
    private boolean isAvailable;
    private int weightInGrams;

}
