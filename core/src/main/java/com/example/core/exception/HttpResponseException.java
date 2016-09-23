package com.example.core.exception;

/**
 * Created by kevinhao on 16/7/20.
 * 服务器返回的code非200异常,本地识别
 */
public class HttpResponseException extends RuntimeException {

    /**
     * 注册手机已存在,直接登录
     */
    public static final int PHONE_EXIST_ERROR = 500;

    private int code;
    private String msg;

    public HttpResponseException(int code, String msg) {
        super("HttpResponse: code——>" + code + "msg——>" + msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
