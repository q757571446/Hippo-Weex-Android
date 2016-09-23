package com.example.core.exception;

/**
 * Created by kevinhao on 16/7/21.
 * 与服务器约定好的异常,服务器报错的异常本地识别
 */
public class ERROR {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;

    /**
     * token过期
     */
    public static final int TOKEN_EXPIRE = 1004;

    /**
     * 缺少请求参数
     */
    public static final int LACK_PARAMETER = 1005;
}