package challenge.server.common.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 패스워드 유효성 검사 프론트와 합의해서 정규 표현식 작성
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,12}$");
        Matcher m = p.matcher(value);
        return m.matches(); // 이렇게 만들어 놨을 때의 문제점 = patchUser 시 password가 null로 오는 경우에도 유효하지 않다고 잡음
    }
}
