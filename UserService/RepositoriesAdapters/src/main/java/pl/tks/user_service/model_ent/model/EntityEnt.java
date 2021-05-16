package pl.tks.user_service.model_ent.model;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class EntityEnt {
    private UUID uuid;
}
