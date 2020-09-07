package com.ee.declaration.web.validator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidInACSIIRange.Validator.class)
public @interface ValidInACSIIRange {

    String message() default "{com.ee.declaration.web.validator.ASCIIValidator.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min();

    int max();

    @RequiredArgsConstructor
    class Validator implements ConstraintValidator<ValidInACSIIRange, String> {

        private int min;
        private int max;

        @Override
        public void initialize(ValidInACSIIRange constraintAnnotation) {
            min = constraintAnnotation.min();
            max = constraintAnnotation.max();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return StringUtils.isEmpty(value) || isInRange(value);
        }

        private boolean isInRange(String value) {
            for (int i = 0; i < value.length(); i++) {
                int asciiCode = value.charAt(i);
                if (asciiCode < min || asciiCode > max) {
                    return false;
                }
            }

            return true;
        }
    }

}
