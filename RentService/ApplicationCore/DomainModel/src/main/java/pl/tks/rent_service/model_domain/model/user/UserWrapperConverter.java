package pl.tks.rent_service.model_domain.model.user;

import java.util.ArrayList;
import java.util.List;

public class UserWrapperConverter {

    public static UserWrapper userConverter(Administrator administrator) {
        return new UserWrapper(administrator.getUuid(), administrator.getName(), administrator.getLastName(), administrator.isActive(), administrator.getLogin(), administrator.getRole());
    }

    public static UserWrapper userConverter(Employee employee) {
        return new UserWrapper(employee.getUuid(), employee.getName(), employee.getLastName(), employee.isActive(), employee.getLogin(), employee.getRole());
    }

    public static UserWrapper clientConverter(Client client) {
        return new UserWrapper(client.getUuid(), client.getName(), client.getLastName(), client.isActive(), client.getLogin(), client.getRole());
    }

    public static List<UserWrapper> listWrapper(List<User> userList) {
        List<UserWrapper> list = new ArrayList<>();
        for (User user : userList) {
            list.add(wrap(user));
        }
        return list;
    }

    public static UserWrapper wrap(User user) {
        if (user instanceof Client) {
            return clientConverter((Client) (user));
        } else if (user instanceof Administrator) {
            return userConverter((Administrator) user);
        } else {
            return userConverter((Employee) user);
        }
    }


}
