package com.shipping.analyser.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class CustomExceptionHandling extends RuntimeException {

    String code;
    String message;
    String devmsg;
    String moreInfo;

    private static final long serialVersionUID = 1L;

    public CustomExceptionHandling(String code, String message, String devMsg, String moreInfo) {
        this(code, message, devMsg);
        this.moreInfo = moreInfo;
    }

    public CustomExceptionHandling(String code, String message, String devMsg) {
        this(code, message);
        this.devmsg = devMsg;
    }

    public CustomExceptionHandling(String code, String message) {
        super( message);
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDevmsg() {
        return devmsg;
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}