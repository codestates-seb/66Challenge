package challenge.server.helper.event;

import challenge.server.user.entity.User;
import lombok.Getter;

@Getter
public class UserRegistrationApplicationEvent {
    private User user;

    public UserRegistrationApplicationEvent(User user) {
        this.user = user;
    }
}
