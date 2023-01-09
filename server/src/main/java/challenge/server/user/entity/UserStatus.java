package challenge.server.user.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userStatusId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "userStatus", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public enum Type {
        ACTIVITY(1),
        QUICK(2),
        BANNED(3);

        @Getter
        private final int num;

        Type(int num) {
            this.num = num;
        }
    }
}
