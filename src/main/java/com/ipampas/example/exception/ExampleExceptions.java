package com.ipampas.example.exception;

import com.ipampas.framework.support.exception.ExceptionDefinition;

/**
 * @author caizj
 * @version V1.0
 * @date 2019/3/21 11:57 PM
 */
public enum ExampleExceptions implements ExceptionDefinition {

    A("", ""),;

    private String code;

    private String message;

    ExampleExceptions(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
