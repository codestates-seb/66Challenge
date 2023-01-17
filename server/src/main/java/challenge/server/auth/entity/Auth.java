package challenge.server.auth.entity;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.challenge.entity.Challenge;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Auth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    private String body;
    private String authImageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID")
    private Challenge challenge;

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public void changeAuth(Auth auth) {
        Optional.ofNullable(auth.getBody())
                .ifPresent(changetBody -> this.body = changetBody);
    }

    public void changeImageUrl(String authImageUrl) {
        this.authImageUrl = authImageUrl;
    }
}
