package ru.kochnev.technomant.SpringBoot.validators;

import ru.kochnev.technomant.SpringBoot.annotations.PasswordConfirm;
import ru.kochnev.technomant.SpringBoot.models.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object> {

    @Override
    public void initialize(PasswordConfirm passwordConfirm) {
    }
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        User user = (User) object;
        return user.getPassword().equals(user.getPasswordConfirm());
    }
}

