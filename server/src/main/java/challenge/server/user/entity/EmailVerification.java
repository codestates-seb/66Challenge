package challenge.server.user.entity;

import challenge.server.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerification extends BaseTimeEntity {
    private static final Long MAX_EXPIRY_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailVerificationId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    private String verificationToken;
    private Boolean isExpired;

    private LocalDateTime expiryTime;

    @Builder
    public EmailVerification(String email, String verificationToken, Boolean isExpired) {
        this.email = email;
        this.verificationToken = verificationToken;
        this.isExpired = isExpired;
        this.expiryTime = LocalDateTime.now().plusMinutes(MAX_EXPIRY_TIME);
    }

    public void useVerificationToken() {
        this.isExpired = true;
    }
}
