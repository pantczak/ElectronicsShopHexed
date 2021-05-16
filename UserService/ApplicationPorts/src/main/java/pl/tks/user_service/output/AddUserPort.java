package pl.tks.user_service.output;



public interface AddUserPort<T1> {
    boolean addClient(T1 user);

    boolean addEmployee(T1 employee);

    boolean addAdministrator(T1 admin);
}
