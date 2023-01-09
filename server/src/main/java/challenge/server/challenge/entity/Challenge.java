package challenge.server.challenge.entity;

import challenge.server.auth.entity.Auth;
import challenge.server.habit.entity.Habit;
import challenge.server.review.entity.Review;
import challenge.server.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HABIT_ID")
    private Habit habit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHALLENGE_STATUS_ID")
    private ChallengeStatus status;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wildcard> wildcards = new ArrayList<>();

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Auth> auths = new ArrayList<>();
}
