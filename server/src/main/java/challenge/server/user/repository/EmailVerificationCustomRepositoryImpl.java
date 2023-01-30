package challenge.server.user.repository;

import challenge.server.user.entity.EmailVerification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

import static challenge.server.security.user.entity.QEmailVerification.emailVerification;


@Repository
@RequiredArgsConstructor
public class EmailVerificationCustomRepositoryImpl implements EmailVerificationCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<EmailVerification> findValidVerificationByEmail(String email, String verificationCode, LocalDateTime currentTime) {
        EmailVerification result = jpaQueryFactory
                .selectFrom(emailVerification)
                .where(emailVerification.email.eq(email),
                        emailVerification.verificationCode.eq(verificationCode),
                        emailVerification.expiryTime.goe(currentTime), // 만료 시간 >= 현재 시간
                        emailVerification.isValidated.eq(false)) // 인증 정보를 아직 사용하지 않음 = 아직 유효한 인증 정보임
                .fetchFirst();

        return Optional.ofNullable(result);
    }
}
