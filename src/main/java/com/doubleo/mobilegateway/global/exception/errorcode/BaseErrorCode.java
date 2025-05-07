package com.doubleo.mobilegateway.global.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus getHttpStatus();

    String getMessage();

    String errorClassName();
}
