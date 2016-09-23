package com.example.core;

/**
 * Created by dell on 2016/9/20.
 */
public class Config {
    public static boolean DEBUG = true;
    public static final String TokenFile = "TOKEN_FILE";

    public static final String BUNDLE_DATA_KEY = "BUNDLE_KEY_ARGS";
    public static final String BUNDLE_TRANSLATE_KEY = "BUNDLE_KEY_TRANSLATE";
    public static final String BUNDLE_PAGE_KEY = "BUNDLE_KEY_ARGS_URI";

    private static final String DEFAULT_IP = "10.17.39.79";
    public static String CURRENT_IP= DEFAULT_IP; // your_current_IP

    public static final String WEEX_ASSET_BASE = "file://assets/";
    public static final String WEEX_SERVER_BASE =  "http://"+CURRENT_IP+":12580/client/build/";

    public static final String WEEX_BASE_URL = WEEX_SERVER_BASE;

    //默认文件
    public static final String WEEX_DEFAULT_PAGE = WEEX_BASE_URL + "page-manager.js";

    //五个入口文件
    public static final String WEEX_INDEX_URL_1 = WEEX_BASE_URL + "recommend/index.js";
    public static final String WEEX_INDEX_URL_2 = WEEX_BASE_URL + "information/index.js";
    public static final String WEEX_INDEX_URL_3 = WEEX_BASE_URL + "cloud/index.js";
    public static final String WEEX_INDEX_URL_4 = WEEX_BASE_URL + "group/index.js";
    public static final String WEEX_INDEX_URL_5 = WEEX_BASE_URL + "person/index.js";
}
