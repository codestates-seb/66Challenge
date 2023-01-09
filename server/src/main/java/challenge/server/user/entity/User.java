package challenge.server.user.entity;

import challenge.server.bookmark.entity.Bookmark;
import challenge.server.challenge.entity.Challenge;
import challenge.server.habit.entity.Habit;
import challenge.server.report.entity.Report;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String username;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();
}
