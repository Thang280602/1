package com.shop.shoes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    private MessageSource messageSource;

    private static final int MIN_PASSWORD_LENGTH = 8;


    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            System.out.println("Current Locale: " + currentLocale);
            String errorMessage = messageSource.getMessage(
                    "validation.user.authentication_code",
                    new Object[]{MIN_PASSWORD_LENGTH},
                    LocaleContextHolder.getLocale()
            );
            System.out.println(errorMessage);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
