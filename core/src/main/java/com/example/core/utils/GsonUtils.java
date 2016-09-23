package com.example.core.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gson工具类，使用自定义注解规则解析bean对象
 */
public class GsonUtils {
    //创建Gson对象，设置Gson解析策略
    public static Gson newInstance(){
        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(
//                new TypeToken<TreeMap<String, Object>>(){}.getType(),
//                new JsonDeserializer<TreeMap<String, Object>>() {
//                    @Override
//                    public TreeMap<String, Object> deserialize(
//
//                    }
//                });
        builder.registerTypeAdapter(Map.class, new MapDeserialize());
//        builder.setFieldNamingStrategy(new AnnotateNaming());
        return builder.create();
    }

    /**
     * 判断是不是int类型的数字
     * @param str
     * @return
     * 	是int类型返回true
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field f) {
            SerializedName a = f.getAnnotation(SerializedName.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(f);
        }
    }


    /**
     * json转为对象时调用
     * @author Ickes
     */
    private static class MapDeserialize implements JsonDeserializer<Map<String,Object>> {

        @Override
        public Map<String, Object> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            Map<String, Object> treeMap = new HashMap<>();
            JsonObject jsonObject = json.getAsJsonObject();
            return populate(jsonObject, treeMap);
        }

        Map<String,Object>  populate(JsonObject jsonObject, Map<String, Object> map){
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                if(value.isJsonPrimitive()){
                    JsonPrimitive pri = (JsonPrimitive) value;
                    if(pri.isNumber()){
                        map.put(key,value.getAsInt());
                    }else if(pri.isBoolean()){
                        map.put(key,value.getAsBoolean());
                    }else if(pri.isString()){
                        map.put(key,value.getAsString());
                    }
                }else if(value.isJsonObject()){
                    Map<String, Object> _map =  new HashMap<>();
                    map.put(key, _map);
                    populate(jsonObject.getAsJsonObject(key), _map);
                }else if(value.isJsonArray()){
                    ArrayList list = new ArrayList();
                    map.put(key, list);
                    populateArray(jsonObject.getAsJsonArray(key), list);
                }else if(value.isJsonNull()){
                    continue;
                }
            }
            return map;
        }

        private void populateArray(JsonArray jsonArray, ArrayList list) {
            Iterator<JsonElement> iterator = jsonArray.iterator();
            while (iterator.hasNext()){
                JsonElement next = iterator.next();
                if(next.isJsonArray()){
                    ArrayList _list = new ArrayList();
                    list.add(_list);
                    populateArray(next.getAsJsonArray(), _list);
                }else if(next.isJsonObject()){
                    HashMap<String,Object> _map = new HashMap<>();
                    list.add(_map);
                    populate(next.getAsJsonObject(), _map);
                }else if(next.isJsonPrimitive()){
                    JsonPrimitive pri = (JsonPrimitive) next;
                    if(pri.isNumber()){
                        list.add(pri.getAsInt());
                    }else if(pri.isBoolean()){
                        list.add(pri.getAsBoolean());
                    }else if(pri.isString()){
                        list.add(pri.getAsString());
                    }
                }else if(next.isJsonNull()){
                    continue;
                }
            }
        }
    }
}