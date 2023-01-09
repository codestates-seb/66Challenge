package challenge.server.report.entity;

import challenge.server.auth.entity.Auth;
import challenge.server.habit.entity.Habit;
import challenge.server.review.entity.Review;
import challenge.server.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HABIT_ID")
    private Habit habit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTH_ID")
    private Auth auth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REVIEW_ID")
    private Review review;

    public enum Type {
        PORNOGRAPHY(1),
        ADVERTISEMENT(2),
        ABUSE(3);

        @Getter
        private int num;

        Type(int num) {
            this.num = num;
        }
    }
}
