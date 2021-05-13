package com.smilegate.coupon.backend.core.exception;

import com.smilegate.coupon.backend.core.dto.ApiResponseDto;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController implements ErrorController {

    @Override
    public String getErrorPath() {
        return null;
    }

    @RequestMapping(value = "/error")
    public ResponseEntity<?> error(HttpServletRequest request) {
        int status = Integer.parseInt(String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)));
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ApiResponseDto<>(ExceptionResponse.create(status)));
    }
}
