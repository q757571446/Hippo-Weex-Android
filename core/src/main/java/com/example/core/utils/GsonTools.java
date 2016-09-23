package com.example.core.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class GsonTools {
    public static JSONObject convertMapToJson(Map<String, Object> map){
        Gson gson = GsonUtils.newInstance();
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(gson.toJson(map));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static String convertMapToString(Map<String, Object> map){
        Gson gson = GsonUtils.newInstance();
        return gson.toJson(map);
    }

    public static <T> T convertJsonToBean(String json, Class<T> clazz) {
        Gson gson = GsonUtils.newInstance();
        return gson.fromJson(json,clazz);
    }

    /**
     * 转化成具有层级结构的Map
     * @param json
     * @return
     */
    public static Map<String,Object> convertJsonToNestMap(JSONObject json) {
        Gson gson = GsonUtils.newInstance();
        return gson.fromJson(json.toString(),Map.class);
    }
    /**
     * 转化成具有层级结构的Map，无序
     * @param json
     * @return
     */
    public static Map<String,Object> convertJsonToNestMap(String json) {
        Gson gson = GsonUtils.newInstance();
        return gson.fromJson(json,Map.class);
    }

    /**
     * 转化成具有层级结构的Map，有序
     * @param json
     * @return
     */
    public static LinkedTreeMap<String,LinkedTreeMap> convertJsonToNestLinkedMap(String json) {
        Gson gson = GsonUtils.newInstance();
        return gson.fromJson(json,LinkedTreeMap.class);
    }

    /**
     * 转化成具有层级结构的Map，有序
     * @param json
     * @return
     */
    public static LinkedTreeMap<String,LinkedTreeMap> convertJsonToNestLinkedMap(JSONObject json) {
        Gson gson = GsonUtils.newInstance();
        return gson.fromJson(json.toString(),LinkedTreeMap.class);
    }
}