package com.example.account.management.common.validation.validator;

import com.example.account.management.common.validation.annotation.ValidatedPageSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageSizeValidator implements ConstraintValidator<ValidatedPageSize, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value > 0 && value <= 100;
    }
}
