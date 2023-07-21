package com.cct.beautysalon.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private Object error;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
