package pl.java.tks.infrastructure;

import java.util.UUID;

public interface EndEventPort {
    void endEvent(UUID uuid);
}
