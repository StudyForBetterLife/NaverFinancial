package com.naver.financial.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T response;
    private ErrorDto error;

    public ResponseDto(boolean success, T response, ErrorDto error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }
}
