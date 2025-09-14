package com.account.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {

    private int statusCode;
    private String message;
    private T result;
    private Long count;

    public void responseMethod(int statusCode, String message, T result, Long count) {
        this.statusCode = statusCode;
        this.setMessage(message);
        this.setResult(result);
        this.setCount(count);
    }
}
