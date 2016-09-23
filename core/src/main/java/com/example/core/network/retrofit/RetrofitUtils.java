package com.example.core.network.retrofit;

import android.content.Context;

import com.example.core.network.URLs;
import com.example.core.network.converter.MyGsonConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by q7575 on 2015/12/18.
 */
public class RetrofitUtils {
  private static Retrofit singleton;

  public static <T> T getInstance(Context context, Class<T> clazz) {
    return getSingleton(context).create(clazz);
  }

  private static Retrofit getSingleton(Context context){
    if (singleton == null) {
      synchronized (RetrofitUtils.class) {
        if (singleton == null) {
          Retrofit.Builder builder = getDefaultBuild(context);
          builder.client(OkHttpUtils.getInstance(context));//默认
          singleton = builder.build();
        }
      }
    }
    return singleton;
  }

  public static Retrofit.Builder getDefaultBuild(Context context){
    Retrofit.Builder builder = new Retrofit.Builder();
    builder.baseUrl(URLs.ENDPOINT);
    builder.addConverterFactory(MyGsonConverterFactory.create());
    builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    return builder;
  }
//
//  public static Retrofit.Builder getWeexBuild(Context context){
//    Retrofit.Builder builder = new Retrofit.Builder();
//    builder.baseUrl(URLs.ENDPOINT);
//    builder.addConverterFactory(MyGsonConverterFactory.create());
//    builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//    return builder;
//  }
}
