package pl.tks.rent_service.user_interfaces;



public interface AddUserPort<T1> {
    boolean addClient(T1 user);

    boolean addEmployee(T1 employee);

    boolean addAdministrator(T1 admin);
}
