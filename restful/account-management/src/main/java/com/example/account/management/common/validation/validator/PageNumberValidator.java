package com.example.account.management.common.validation.validator;

import com.example.account.management.common.validation.annotation.ValidatedPageNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PageNumberValidator implements ConstraintValidator<ValidatedPageNumber, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= 0;
    }
}
