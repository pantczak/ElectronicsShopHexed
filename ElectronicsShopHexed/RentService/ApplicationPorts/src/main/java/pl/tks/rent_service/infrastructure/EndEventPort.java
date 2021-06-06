package pl.tks.rent_service.infrastructure;

import java.util.UUID;

public interface EndEventPort {
    void endEvent(UUID uuid);
}
