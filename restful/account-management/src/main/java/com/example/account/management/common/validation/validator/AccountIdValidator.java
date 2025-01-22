package com.example.account.management.common.validation.validator;

import com.example.account.management.common.validation.annotation.ValidatedAccountId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountIdValidator implements ConstraintValidator<ValidatedAccountId, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
