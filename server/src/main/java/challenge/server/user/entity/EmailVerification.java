package challenge.server.user.entity;

import challenge.server.audit.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailVerification extends BaseTimeEntity {
    private static final Long MAX_EXPIRY_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailVerificationId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    private String verificationCode;
    private Boolean isExpired;

    private LocalDateTime expiryTime;

    public EmailVerification createEmailVerification(String email, String verificationCode, Boolean isExpired) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.isExpired = isExpired;
        this.expiryTime = LocalDateTime.now().plusMinutes(MAX_EXPIRY_TIME);

        return this;
    }

    public void useVerificationCode() {
        this.isExpired = true;
    }
}
