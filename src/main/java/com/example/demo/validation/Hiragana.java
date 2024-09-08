package com.example.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = HiraganaValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Hiragana {
    String message() default "ひらがなのみ使用できます";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
