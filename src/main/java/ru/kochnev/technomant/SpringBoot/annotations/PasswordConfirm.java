package ru.kochnev.technomant.SpringBoot.annotations;

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
@Constraint(validatedBy = PasswordConfirmValidator.class)
@Documented
public @interface PasswordConfirm {
    String message() default "fields 'password' and 'password confirm' do not match";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}