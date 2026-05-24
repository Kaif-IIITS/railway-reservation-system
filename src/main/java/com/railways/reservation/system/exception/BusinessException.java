package com.railways.reservation.system.exception;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
    private Object[] args;

    public BusinessException(ErrorCode errorCode, Object... args){
        super(getFromatterMessage(errorCode,args));
        this.errorCode = errorCode;
        this.args = args;
    }

    private static String getFromatterMessage(ErrorCode errorCode, Object[] args) {
        if (args != null && args.length > 0) {
            return String.format(errorCode.getMessage(),args);
        }
        return errorCode.getMessage();
    }
}
