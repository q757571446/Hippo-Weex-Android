package com.example.core.network;

/**
 * Created by kevin on 16-7-31.
 */
public class Config {
    public static final String RESPONSE_CACHE = "RESPONSE_CACHE";
    public static final long RESPONSE_CACHE_SIZE = 1024*1024*10;
    public static final long MAX_CONNECTION_TIMEOUT = 3000L;
    public static boolean DEBUG = true;

    public static final String LOG_PATH = "cache/Log/";

    public static int LOG_CACHE_COUNT = 100;//缓存报文数量

}
