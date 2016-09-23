package com.example.core.network.converter;

import java.io.IOException;

import retrofit2.Converter;

/**
 * Created by q7575 on 2016/5/10.
 */
public class MyGsonStringConverter<T> implements Converter<T, String> {
  @Override
  public String convert(T value) throws IOException {
    return "==";
  }
}
