package pl.tks.rent_service.infrastructure;

import java.util.UUID;

public interface DeleteEventPort {
    boolean deleteEvent(UUID uuid);
}
