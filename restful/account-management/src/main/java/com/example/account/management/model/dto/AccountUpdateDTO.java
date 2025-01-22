package com.example.account.management.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountUpdateDTO {

    @Schema(description = "The name of the account.", example = "John Doe")
    @Size(min = 3, max = 50, message="Name must be between 3 and 50 characters.")
    @Pattern(regexp = "^[\\p{Alpha} ]*$", message = "Name must contain only letters and spaces.")
    private String name;
}
