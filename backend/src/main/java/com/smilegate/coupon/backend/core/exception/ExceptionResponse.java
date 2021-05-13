package com.smilegate.coupon.backend.core.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ExceptionResponse {

    private final List<String> messages;

    public ExceptionResponse(List<String> messages) {
        this.messages = messages;
    }

    public static ExceptionResponse create(int status) {
        return new ExceptionResponse(List.of(getStatusMessage(status)));
    }

    public static ExceptionResponse create(String message) {
        return new ExceptionResponse(List.of(message));
    }

    public static String getStatusMessage(int status) {
        switch (status) {
            case 404:
                return "존재하지 않는 url 입니다.";
            default:
                return "서버 문제로 정상 처리되지 않았습니다. 관리자에게 문의해 주시기 바랍니다.";
        }
    }

}
