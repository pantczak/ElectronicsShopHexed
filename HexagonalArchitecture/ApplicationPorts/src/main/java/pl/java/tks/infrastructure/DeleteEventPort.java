package pl.java.tks.infrastructure;

import java.util.UUID;

public interface DeleteEventPort {
    boolean deleteEvent(UUID uuid);
}
