package com.example.account.management.common.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = com.example.account.management.common.validation.validator.AccountIdValidator.class)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatedAccountId {
    String message() default "Invalid account id, must be greater than 0.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
