package com.naver.financial.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorDto {
    private int status;
    private String message;

    public ErrorDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
