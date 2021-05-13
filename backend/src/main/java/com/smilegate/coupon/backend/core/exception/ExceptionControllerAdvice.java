package com.smilegate.coupon.backend.core.exception;

import com.smilegate.coupon.backend.core.dto.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> methodArgumentTypeMismatchException(HttpServletRequest request,
                                                                                                 MethodArgumentTypeMismatchException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        String message = String.format(
                "%s is required %s type.",
                exception.getName(),
                Objects.requireNonNull(exception.getRequiredType()).getSimpleName()
        );
        return createResponseEntity(HttpStatus.BAD_REQUEST, ExceptionResponse.create(message));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> methodArgumentNotValidException(HttpServletRequest request,
                                                                                             MethodArgumentNotValidException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        BindingResult errors = exception.getBindingResult();
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : errors.getFieldErrors()) {
            messages.add(fieldError.getDefaultMessage());
        }
        return createResponseEntity(HttpStatus.BAD_REQUEST, new ExceptionResponse(messages));
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> responseStatusException(HttpServletRequest request,
                                                                                     ResponseStatusException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        return createResponseEntity(exception.getStatus(), ExceptionResponse.create(exception.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> handleException(HttpServletRequest request,
                                                                             Exception exception) {
        printLog(request, exception);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return createResponseEntity(status, ExceptionResponse.create(status.value()));
    }


    private <T> ResponseEntity<ApiResponseDto<T>> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponseDto<>(body));
    }


    private void printLog(HttpServletRequest request, Exception exception) {
        log.error("[ ERROR ] {} {}", request.getMethod(), request.getRequestURI(), exception);
    }


}
