package pl.tks.rent_service.user_interfaces;


public interface DeleteEventPort<T1> {
    boolean deleteEvent(T1 event);
}
