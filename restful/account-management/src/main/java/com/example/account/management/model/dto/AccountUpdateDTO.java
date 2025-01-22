package com.example.account.management.model.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateDTO {

    @Size(min = 3, max = 50, message="Name must be between 3 and 50 characters.")
    @Pattern(regexp = "^[\\p{Alpha} ]*$", message = "Name must contain only letters and spaces.")
    private String name;
}
