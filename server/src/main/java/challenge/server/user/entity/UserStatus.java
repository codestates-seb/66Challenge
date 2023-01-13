package challenge.server.user.entity;

import challenge.server.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserStatus extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userStatusId;

    @Enumerated(EnumType.STRING)
    private Type type;

//    @OneToMany(mappedBy = "userStatus", cascade = CascadeType.ALL)
//    private List<User> users = new ArrayList<>();

    public enum Type {
        ACTIVE(1),
        QUIT(2),
        BANNED(3);

        @Getter
        private final int num;

        Type(int num) {
            this.num = num;
        }
    }
}
