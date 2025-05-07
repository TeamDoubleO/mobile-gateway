package com.doubleo.mobilegateway.global.exception;

import com.doubleo.mobilegateway.global.exception.errorcode.BaseErrorCode;
import com.doubleo.mobilegateway.global.exception.errorcode.GlobalErrorCode;
import com.doubleo.mobilegateway.global.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@RestControllerAdvice(basePackages = "com.doubleo")
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponse<?>> handleCommonException(CommonException e) {
        BaseErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse =
                ErrorResponse.of(errorCode.errorClassName(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(CommonResponse.onFailure(errorCode.getHttpStatus().value(), errorResponse));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<CommonResponse<?>> handleWebExchangeBindException(
            WebExchangeBindException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = ErrorResponse.of(e.getClass().getSimpleName(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.onFailure(HttpStatus.BAD_REQUEST.value(), errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleException(Exception e) {
        log.error("Unhandled exception", e);
        ErrorResponse errorResponse =
                ErrorResponse.of(
                        e.getClass().getSimpleName(),
                        GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(
                        CommonResponse.onFailure(
                                GlobalErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value(),
                                errorResponse));
    }
}
