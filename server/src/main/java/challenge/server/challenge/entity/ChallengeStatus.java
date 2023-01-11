package challenge.server.challenge.entity;

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
public class ChallengeStatus extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeStatusId;

    @Enumerated(EnumType.STRING)
    private Type status;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL)
    private List<Challenge> challenges = new ArrayList<>();

    public void addChallenge(List<Challenge> challenges) {
        if (this.challenges != null) {
            this.challenges.addAll(challenges);
        }
        else this.challenges = challenges;
    }

    public enum Type {
        CHALLENGE(1),
        SUCCESS(2),
        FAIL(3);

        @Getter
        private final int num;

        Type(int num) {
            this.num = num;
        }
    }
}
