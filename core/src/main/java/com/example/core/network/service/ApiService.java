package com.example.core.network.service;


import org.json.JSONObject;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {
    @GET("common/UserServiceImpl/getCaptcha")
    Observable<JSONObject> receivePicCode();

    @Headers({"Content-Type: application/json"})
    @POST("common/LoginServiceImpl/login")
    Observable<JSONObject> login(@Body Map<String, Object> map);
}
