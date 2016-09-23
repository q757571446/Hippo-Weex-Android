package com.example.core.exception;

/**
 * Created by kevinhao on 16/7/21.
 */
public class ApiException extends Exception {
    private int code;
    //用于展示的异常信息
    private String displayMessage;

    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException(Throwable throwable, int code) {
        this(throwable);
        this.code = code;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public int getCode() {
        return code;
    }
}
