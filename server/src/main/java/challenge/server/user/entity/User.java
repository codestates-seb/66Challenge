package challenge.server.user.entity;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.bookmark.entity.Bookmark;
import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.Habit;
import challenge.server.report.entity.Report;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false) // todo 길이 제약조건 협의
    private String password;

    @Column(nullable = false, unique = true)
    private String username;

    // JWT 구현 시 추가
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    /*
    // JWT 구현 시 추가
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // JWT 구현 시 추가
    public void setEmail(String email) {
        this.email = email;
    }

    // JWT 구현 시 추가
    public void setPassword(String password) {
        this.password = password;
    }

    // JWT 구현 시 추가
    public void setUsername(String username) {
        this.username = username;
    }

    // JWT 구현 시 추가
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERS_STATUS_ID")
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Habit> habits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Challenge> challenges = new ArrayList<>();
}
