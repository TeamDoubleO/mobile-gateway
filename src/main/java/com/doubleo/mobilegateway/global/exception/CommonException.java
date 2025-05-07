package com.doubleo.mobilegateway.global.exception;

import com.doubleo.mobilegateway.global.exception.errorcode.GrpcErrorCode;
import com.doubleo.patientservice.global.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public CommonException(GrpcErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
