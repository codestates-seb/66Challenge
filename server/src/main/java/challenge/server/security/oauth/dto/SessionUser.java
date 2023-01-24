package challenge.server.security.oauth.dto;

import challenge.server.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 직렬화 기능을 가진 User 클래스
 * User 클래스에 직렬화하지 않는 이유는 다른 엔티티와도 연관관계가 생길 수 있는 엔티티이기 때문에 성능 이슈, 사이드 이펙트가 발생할 확률이 높기 때문이다.
 */
@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    private String profileImageUrl;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
