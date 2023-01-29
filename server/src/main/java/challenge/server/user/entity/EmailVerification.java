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

    private String email;

    private String verificationCode;
    private Boolean isValidated; // todo 의미적으로 이 속성명을 isValidated(검증 완료 여부)로 바꾸거나, 이 속성을 활용(만료되었을 때 자동 업데이트 되게?)

    private LocalDateTime expiryTime;

    public EmailVerification createEmailVerification(String email, String verificationCode, Boolean isValidated) {
        this.email = email;
        this.verificationCode = verificationCode;
        this.isValidated = isValidated;
        this.expiryTime = LocalDateTime.now().plusMinutes(MAX_EXPIRY_TIME);

        return this;
    }

    public void useVerificationCode() {
        this.isValidated = true;
    }
}
