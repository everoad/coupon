package com.smilegate.coupon.backend.core.dto;

import lombok.Getter;

@Getter
public class ExceptionResponseDto<T> {

    private final T message;

    public ExceptionResponseDto(T message) {
        this.message = message;
    }

}
