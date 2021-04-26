package com.msampietro.springmultipleconnectionpools.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ApiError {

    private final Integer status;
    private final String message;
    private final String type;
    private final String path;
    private long timestamp = Instant.now().toEpochMilli();

    public ApiError(Integer status, String message, String type, String path) {
        this.status = status;
        this.message = message;
        this.type = type;
        this.path = path;
    }

}
