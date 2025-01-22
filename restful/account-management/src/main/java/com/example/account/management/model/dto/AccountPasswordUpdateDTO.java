package com.example.account.management.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountPasswordUpdateDTO {

    @Schema(description = "The old password of the account.", example = "oldPassword")
    private String oldPassword;

    @Schema(description = "The new password of the account.", example = "newPassword")
    @NotBlank(message = "Password is required.")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters.")
    private String newPassword;
}
