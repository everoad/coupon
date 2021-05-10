package com.smilegate.coupon.backend.core.dto;

import lombok.Getter;

@Getter
public class ApiResponseDto<T> {

    private T body;

    public ApiResponseDto() {
    }

    public ApiResponseDto(T body) {
        this.body = body;
    }

}
