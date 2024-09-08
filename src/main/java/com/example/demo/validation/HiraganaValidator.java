package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HiraganaValidator implements ConstraintValidator<Hiragana, String> {

    private static final String HIRAGANA_PATTERN = "^[ぁ-んー]+$";

    @Override
    public void initialize(Hiragana constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // @NotEmpty でチェックされるためここではOKとする
        }
        return value.matches(HIRAGANA_PATTERN);
    }
}
