package ru.kochnev.technomant.SpringBoot.annotations;

import ru.kochnev.technomant.SpringBoot.validators.NameLengthValidator;
import ru.kochnev.technomant.SpringBoot.validators.PasswordConfirmValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NameLengthValidator.class)
@Documented
public @interface NameLengthValid {
    String message() default "the name should not exceed 100 characters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}