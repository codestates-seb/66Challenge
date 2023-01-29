package challenge.server.report.entity;

import challenge.server.audit.BaseTimeEntity;
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
public class Report extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private Long postId;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(name = "reporter_user_id")
    private Long reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id")
    private User reported;

    public enum ReportType {
        UNCORRECT(1),
        DUPLICATION(2),
        AD(3),
        ABUSE(4),
        OBSCENE(5),
        PERSONALINFO(6),
        ETC(7);

        @Getter
        private int num;

        ReportType(int num) {
            this.num = num;
        }
    }

    public enum PostType {
        HABIT(1),
        REVIEW(2),
        AUTH(3);

        @Getter
        private int num;

        PostType(int num) {
            this.num = num;
        }
    }
}