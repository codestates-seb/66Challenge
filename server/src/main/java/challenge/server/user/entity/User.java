package challenge.server.user.entity;

import challenge.server.audit.BaseTimeEntity;
import challenge.server.bookmark.entity.Bookmark;
import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.Habit;
import challenge.server.report.entity.Report;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter // JWT, CustomBeanUtils
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@DynamicInsert
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = true)  // OAuth2 User 등록을 위해 Password nullable true로 변경
    private String password;

    private String profileImageUrl;

    @Column(length = 255, nullable = false, unique = true)
    private String username;

    @ColumnDefault(value = "0")
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 회원 가입 시 이메일 인증 관련
    private String refreshToken;
    private Boolean isEmailVerified;

    // JWT 구현 시 추가
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault(value = "'ACTIVE'")
    private Status status/* = Status.ACTIVE*/;

    // todo 회원 탈퇴/강퇴 시 찜하기, 신고 내역, challenges(+인증 내역, 후기)은 삭제 + 해당 회원이 개설한 habit은 놔두나?
    // 회원 상태를 3(banned)으로 바꾸는 조건/event 정의/구현?
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "reported", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reportedReports = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Habit> habits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Challenge> challenges = new ArrayList<>();

    public User update(String name, String profileImageUrl) {
        this.username = name;
        return this;
    }

    public void deleteRoles() {
        this.roles = null;
    }

    // 회원 가입 시 이메일 인증 관련
    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void succeedEmailVerification() {
        this.isEmailVerified = true;
    }

    public enum Status {
        ACTIVE(1),
        QUIT(2),
        BANNED(3);

        @Getter
        private final int num;

        Status(int num) {
            this.num = num;
        }
    }

    public enum Gender {
        MALE(1),
        FEMALE(2);

        @Getter
        private final int num;

        Gender(int num) {
            this.num = num;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", status=" + status +
                '}';
    }
}
