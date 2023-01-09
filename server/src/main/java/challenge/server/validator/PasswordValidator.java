package challenge.server.validator;

import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // todo 패스워드 유효성 검사 프론트와 합의해서 정규 표현식 작성
        return false;
    }
}
