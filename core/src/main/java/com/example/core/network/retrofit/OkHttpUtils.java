package com.example.core.network.retrofit;

import android.content.Context;

import com.example.core.network.Config;
import com.example.core.network.NetLogInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by q7575 on 2015/12/18.
 */
public class OkHttpUtils {
    private static OkHttpClient singleton;

    public static OkHttpClient getInstance(Context context) {
        return getSingleton(context);
    }

    private static OkHttpClient getSingleton(Context context){
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    OkHttpClient.Builder builder = getDefaultBuild(context);
                    singleton=builder.build();
                }
            }
        }
        return singleton;
    }

    public static OkHttpClient.Builder getDefaultBuild(Context context){
        File cacheDir = new File(context.getCacheDir(), Config.RESPONSE_CACHE);
        NetLogInterceptor httpLoggingInterceptor = new NetLogInterceptor();
        if (Config.DEBUG) {
            httpLoggingInterceptor.setLevel(NetLogInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(NetLogInterceptor.Level.NONE);
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(new Cache(cacheDir, Config.RESPONSE_CACHE_SIZE));
        builder.connectTimeout(Config.MAX_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(httpLoggingInterceptor);
        return builder;
    }
}
