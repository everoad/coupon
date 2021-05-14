package com.smilegate.coupon.backend.core.exception;

import com.smilegate.coupon.backend.core.dto.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.initDirectFieldAccess();
    }

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

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> missingPathVariableException(HttpServletRequest request,
                                                                                          MissingPathVariableException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        return createResponseEntity(HttpStatus.BAD_REQUEST, ExceptionResponse.create("올바른 리소스 ID를 입력해 주세요."));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> constraintViolationException(HttpServletRequest request,
                                                                                          ConstraintViolationException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        return createResponseEntity(HttpStatus.BAD_REQUEST, new ExceptionResponse(List.of(exception.getMessage())));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> bindException(HttpServletRequest request,
                                                                           BindException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        BindingResult bindingResult = exception.getBindingResult();
        List<String> messages = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            messages.add(fieldError.getDefaultMessage());
        }
        for (ObjectError globalError : bindingResult.getGlobalErrors()) {
            messages.add(globalError.getDefaultMessage());
        }
        return createResponseEntity(HttpStatus.BAD_REQUEST, new ExceptionResponse(messages));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> responseStatusException(HttpServletRequest request,
                                                                                     ResponseStatusException exception) {
        if (log.isDebugEnabled()) {
            printLog(request, exception);
        }
        return createResponseEntity(exception.getStatus(), ExceptionResponse.create(exception.getReason()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<ExceptionResponse>> exception(HttpServletRequest request,
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
