package pl.tks.user_service.mq;

import pl.tks.user_service.service.interfaces.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ApplicationScoped
public class RabbitSubscriber implements Serializable {

    private UserService userService;

    @Inject
    public RabbitSubscriber(UserService userService) {
        this.userService = userService;
    }

    //TODO add RabbitMQ logic for subscribing and disposing
}
