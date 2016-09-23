package com.example.core.network.converter;

import com.example.core.model.Result;
import com.example.core.exception.HttpResponseException;
import com.example.core.utils.StreamUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by q7575 on 2016/5/10.
 */
final class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final TypeAdapter<T> adapter;
  private final Type type;

  MyGsonResponseBodyConverter(Type type, Gson gson, TypeAdapter<T> adapter) {
    this.gson = gson;
    this.adapter = adapter;
    this.type = type;
  }

  /**
   * 解析时判断
   */
  @Override
  public T convert(ResponseBody value) throws IOException {
    TypeToken<?> typeToken = TypeToken.get(type);
    Class<?> rawType = typeToken.getRawType();

    String reponse = value.string();

    if(rawType.isAssignableFrom(String.class)){
      return (T) reponse;
    }

    JsonReader jsonReader = null;

    try {
        JSONObject jsonObject = convertJSONObject(reponse);

        if (rawType.isAssignableFrom(JSONObject.class)) {
          return (T) jsonObject.getJSONObject("data");
        }else if (rawType.isAssignableFrom(JSONArray.class)) {
          return (T) jsonObject.getJSONArray("data");
        } else if (rawType.isAssignableFrom(String.class)) {
          return (T) jsonObject.getString("data");
        }else if (rawType.isAssignableFrom(Result.class)) {
          jsonReader = gson.newJsonReader(value.charStream());
        } else{
          jsonReader = gson.newJsonReader(StreamUtils.stringToReader(jsonObject.getString("data"), "UTF-8"));
        }

      T read = adapter.read(jsonReader);

      return read;
    } catch (JSONException e) {
      throw new JsonParseException("ResponseBody：" + reponse + "is not a JSONObject");
    }finally {
      value.close();
    }
  }

  private JSONObject convertJSONObject(String json) throws JSONException {
    JSONObject jsonObject = new JSONObject(json);
    if (jsonObject != null && jsonObject.getInt("code") != 200) {
      throw new HttpResponseException(jsonObject.getInt("code"),jsonObject.getString("msg"));
    }
    return jsonObject;
  }

}