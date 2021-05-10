package com.smilegate.coupon.backend.core.exception;

import com.smilegate.coupon.backend.core.dto.ExceptionResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionControllerAdvice {



//    /**
//     * Validation Exception.
//     * - @Valid, @Validation 에서 발생한 Exception을 처리한다.
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ExceptionResponseDto<Map<String, String>>> methodArgumentNotValidException(HttpServletRequest request,
//                                                                                MethodArgumentNotValidException exception) {
//        if (log.isDebugEnabled()) {
//            printLog(request, exception);
//        }
//        BindingResult errors = exception.getBindingResult();
//        Map<String, String> messages = new HashMap<>();
//        for (FieldError fieldError : errors.getFieldErrors()) {
//            messages.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }
//        return createResponseEntity(HttpStatus.BAD_REQUEST, messages);
//    }
//
//
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ExceptionResponseDto> responseStatusException(HttpServletRequest request, ResponseStatusException exception) {
//        if (log.isDebugEnabled()) {
//            printLog(request, exception);
//        }
//        return createResponseEntity(exception.getStatus(), responseFactory.create(exception));
//    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto<String>> handleException(HttpServletRequest request, Exception exception) {
        printLog(request, exception);
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }


    private <T> ResponseEntity<ExceptionResponseDto<T>> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponseDto<>(body));
    }


    private void printLog(HttpServletRequest request, Exception exception) {
        log.error("[ ERROR ] {} {}", request.getMethod(), request.getRequestURI(), exception);
    }



}
