package pl.java.tks.user_interfaces;

import java.util.UUID;

public interface EndEventPort<T1> {
    void endEvent(T1 event);
}
