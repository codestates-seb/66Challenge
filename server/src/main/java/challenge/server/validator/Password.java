package challenge.server.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {
    String message() default "비밀번호 조건은 이러합니다"; // todo 패스워드 유효성 검사 프론트와 합의해서 메시지 작성

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
