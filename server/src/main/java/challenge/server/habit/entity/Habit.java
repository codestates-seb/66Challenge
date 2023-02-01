package challenge.server.habit.entity;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.bookmark.entity.Bookmark;
import challenge.server.category.entity.Category;
import challenge.server.challenge.entity.Challenge;
import challenge.server.review.entity.Review;
import challenge.server.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Setter
public class Habit extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;
    private String title;
    private String subTitle;

    @Lob
    private String body;

    @Lob
    private String bodyHtml;
    private LocalTime authStartTime;
    private LocalTime authEndTime;
    private String authType;
    private Integer challengers;
    private Double avgScore;

    private String thumbImgUrl;
    private String succImgUrl;
    private String failImgUrl;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User host;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges = new ArrayList<>();

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public void calcAvgScore() {
        int count = reviews.size();
        int score = reviews.stream().map(Review::getScore).mapToInt(i -> i).sum();
        this.avgScore = (double) (round((score / count) * 10) / 10);
    }

    public void changeChallengers(int challengers) {
        this.challengers = challengers;
    }
}