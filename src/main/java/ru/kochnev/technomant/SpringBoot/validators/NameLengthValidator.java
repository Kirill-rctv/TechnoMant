package ru.kochnev.technomant.SpringBoot.validators;

import org.springframework.stereotype.Component;
import ru.kochnev.technomant.SpringBoot.annotations.NameLengthValid;
import ru.kochnev.technomant.SpringBoot.models.Article;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NameLengthValidator implements ConstraintValidator<NameLengthValid, Object> {

    @Override
    public void initialize(NameLengthValid nameLengthValid) {
    }
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        Article article = (Article) object;
        return article.getName().length() <= 100;
    }
}
