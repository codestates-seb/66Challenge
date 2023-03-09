package challenge.server.common.helper.event;

import challenge.server.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserRegistrationApplicationEvent {
    private User user;

    public UserRegistrationApplicationEvent(User user) {
        this.user = user;
    }
}
