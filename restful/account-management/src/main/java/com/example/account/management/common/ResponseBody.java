package com.example.account.management.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {

    private String status;
    private String message;
    private T data;
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
