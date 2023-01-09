package challenge.server.auth.entity;

import challenge.server.challenge.entity.Challenge;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_ID")
    private Challenge challenge;
}
