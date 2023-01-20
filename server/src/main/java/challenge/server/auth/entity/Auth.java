package challenge.server.auth.entity;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.challenge.entity.Challenge;
import lombok.*;

import javax.persistence.*;
import java.util.Optional;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Auth extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String authImageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID")
    private Challenge challenge;

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public void changeImageUrl(String authImageUrl) {
        this.authImageUrl = authImageUrl;
    }
}
