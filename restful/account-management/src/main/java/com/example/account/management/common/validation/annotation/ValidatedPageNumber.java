package com.example.account.management.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = com.example.account.management.common.validation.validator.PageNumberValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedPageNumber {

    String message() default "Invalid page number, must be greater than or equal to 0.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
