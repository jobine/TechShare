package com.example.account.management.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    @Schema(description = "The ID of the account.", example = "1")
    private Long id;

    @Schema(description = "The name of the account.", example = "John Doe")
    private String name;

    @Schema(description = "The email of the account.", example = "john.doe@example.com")
    private String email;
}
