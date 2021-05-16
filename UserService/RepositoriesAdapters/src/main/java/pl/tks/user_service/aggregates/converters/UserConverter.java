package pl.tks.user_service.aggregates.converters;


import pl.tks.user_service.model_ent.model.user.AdministratorEnt;
import pl.tks.user_service.model_ent.model.user.ClientEnt;
import pl.tks.user_service.model_ent.model.user.EmployeeEnt;
import pl.tks.user_service.model_ent.model.user.UserEnt;
import pl.tks.user_service.model_domain.model.user.Administrator;
import pl.tks.user_service.model_domain.model.user.Client;
import pl.tks.user_service.model_domain.model.user.Employee;
import pl.tks.user_service.model_domain.model.user.User;

public class UserConverter {

    private UserConverter() {
    }

    public static UserEnt convertUserToEnt(User user) {
        UserEnt result = null;
        if (user instanceof Client) {
            ClientEnt clientEnt = new ClientEnt(user.getName(), user.getLastName(), user.getLogin(), user.getPassword(), ((Client) user).getAge());
            clientEnt.setActive(user.isActive());
            clientEnt.setUuid(user.getUuid());
            result = clientEnt;
        }
        if (user instanceof Employee) {
            EmployeeEnt employeeEnt = new EmployeeEnt(user.getName(), user.getLastName(), user.getLogin(), user.getPassword());
            employeeEnt.setActive(user.isActive());
            employeeEnt.setUuid(user.getUuid());
            result = employeeEnt;
        }
        if (user instanceof Administrator) {
            AdministratorEnt administratorEnt = new AdministratorEnt(user.getName(), user.getLastName(), user.getLogin(), user.getPassword());
            administratorEnt.setActive(user.isActive());
            administratorEnt.setUuid(user.getUuid());
            result = administratorEnt;
        }
        return result;
    }

    public static User convertEntToUser(UserEnt user) {
        User result = null;
        if (user instanceof ClientEnt) {
            Client client = new Client(user.getName(), user.getLastName(), user.getLogin(), user.getPassword(), ((ClientEnt) user).getAge());
            client.setActive(user.isActive());
            client.setUuid(user.getUuid());
            result = client;
        }
        if (user instanceof EmployeeEnt) {
            Employee employee = new Employee(user.getName(), user.getLastName(), user.getLogin(), user.getPassword());
            employee.setActive(user.isActive());
            employee.setUuid(user.getUuid());
            result = employee;
        }
        if (user instanceof AdministratorEnt) {
            Administrator administrator = new Administrator(user.getName(), user.getLastName(), user.getLogin(), user.getPassword());
            administrator.setActive(user.isActive());
            administrator.setUuid(user.getUuid());
            result = administrator;
        }
        return result;
    }
}
