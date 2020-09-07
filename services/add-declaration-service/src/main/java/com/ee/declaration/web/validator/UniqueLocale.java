package com.ee.declaration.web.validator;

import com.ee.declaration.web.dto.TranslatableStringDto;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueLocale.Validator.class)
public @interface UniqueLocale {

    String message() default "{com.ee.declaration.web.validator.UniqueLocale.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<UniqueLocale, List<? extends TranslatableStringDto>> {

        @Override
        public boolean isValid(List<? extends TranslatableStringDto> value, ConstraintValidatorContext context) {
            return value == null ||
                    value.isEmpty() ||
                    value.stream().map(TranslatableStringDto::getLocale).anyMatch(Objects::isNull) ||
                    value.stream()
                    .collect(Collectors.groupingBy(TranslatableStringDto::getLocale))
                    .values()
                    .stream()
                    .noneMatch(translations -> translations.size() > 1);
        }
    }

}
