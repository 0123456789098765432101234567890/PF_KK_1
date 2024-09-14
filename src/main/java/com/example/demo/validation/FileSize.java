package com.example.demo.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = FileSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileSize {
    String message() default "ファイルサイズが大きすぎます。2MB以内にしてください。";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    long maxSize(); // ファイルの最大サイズ
}
