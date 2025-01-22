package com.example.account.management.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {

    @Schema(description = "The status of the response.", example = "success")
    private String status;

    @Schema(description = "The message of the response error.", nullable = true, defaultValue = "null")
    private String message;

    @Schema(description = "The data of the response.", example = "AccountDTO")
    private T data;

    @Schema(description = "The timestamp of the response.", example = "2021-12-31T23:59:59.999")
    private LocalDateTime timestamp;

    public ResponseBody(LocalDateTime timestamp, String message) {
        this.status = "error";
        this.message = message;
        this.timestamp = timestamp;
    }

    public ResponseBody(T data) {
        this.status = "success";
        this.data = data;
    }
}
