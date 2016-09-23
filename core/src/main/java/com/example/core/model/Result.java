package com.example.core.model;

/**
 * Created by Kevin on 2016/6/10.
 */
public class Result<E>{
    int code;
    String msg;
    E data;

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

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code:'" + code + '\'' +
                ", msg:'" + msg + '\'' +
                ", data:" + data +
                '}';
    }
}
