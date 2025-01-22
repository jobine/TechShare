package com.example.account.management.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateDTO {

    @Schema(description = "The name of the account.", example = "John Doe")
    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 50, message="Name must be between 3 and 50 characters.")
    @Pattern(regexp = "^[\\p{Alpha} ]*$", message = "Name must contain only letters and spaces.")
    private String name;

    @Schema(description = "The email of the account.", example = "john.doe@example.com")
    @NotBlank(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @Schema(description = "The password of the account.", example = "password")
    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters.")
    private String password;
}
