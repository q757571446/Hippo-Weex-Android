package com.example.hippoweex;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.core.Config;
import com.example.core.model.Entity;
import com.example.core.utils.GsonTools;
import com.example.hippoweex.ioc.dagger.PerApplication;
import com.example.hippoweex.ui.view.SimpleBackPage;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@PerApplication
public class Navigator {
    private static final String WEEX_CATEGORY="com.taobao.android.intent.category.WEEX";

    @Inject
    public Navigator() {

    }

    public static void pushMainActivity(Context context, List<String> tabs){
        Intent intent = new Intent(context, MainActivity.class);
        Map<String,Object> data = new HashMap<>();
        data.put("tab-list",tabs);
        setIntentData(intent,data);
        context.startActivity(intent);
        closeAllSimpleAcitivity(context);
    }

    public static void closeAllSimpleAcitivity(Context context){
        Intent intent = new Intent("close");
        context.sendBroadcast(intent);
    }

    public static void pushSimpleBackPage(Context context, SimpleBackPage page){
        pushSimpleBackPage(context, page, new HashMap<String, Object>());
    }

    public static void pushSimpleBackPage(final Context context, SimpleBackPage page, JSONObject intentData){
        Map<String,Object> map = new HashMap<>();
        if(intentData != null){
            map = GsonTools.convertJsonToNestMap(intentData);
        }
        pushSimpleBackPage(context, page, map);
    }

    public static void pushSimpleBackPage(Context context, SimpleBackPage page, Map<String,Object> intentData){
        if (page != null) {
            Uri pageUri = page.getPageUriWithTitle(context);
            Intent intent = new Intent(Intent.ACTION_VIEW, pageUri);
            intent.addCategory(WEEX_CATEGORY);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(pageUri);
            if(intentData != null){
                Bundle bundle = new Bundle();
                //Fragment为主的跳转，界面间传递的数据放在Bundle中，以便于getArguments获取
                //将数据按类型存入，以方便
                setBundleData(bundle, intentData);
                //将所有数据作为json字符串一起存入
                bundle.putString(Config.BUNDLE_TRANSLATE_KEY,GsonTools.convertMapToString(intentData));
                intent.putExtra(Config.BUNDLE_DATA_KEY,bundle);
            }
            context.startActivity(intent);
        }
    }

    public static void pushSimpleBackPage(Context context, String url){
        pushSimpleBackPage(context,url, null);
    }

    /**
     * Weex跳转方法,目标界面究竟是Native还是Weex在SimpleBackActivity中做翻译
     * @param context
     * @param url
     * @param intentData
     */
    public static void pushSimpleBackPage(Context context, String url, JSONObject intentData){
        if(!TextUtils.isEmpty(url)){
            Uri pageUri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, pageUri);
            intent.addCategory(WEEX_CATEGORY);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(pageUri);
            if(intentData != null){
                Bundle bundle = new Bundle();
                //Fragment为主的跳转，界面间传递的数据放在Bundle中，以便于getArguments获取
                Map<String,Object> map = GsonTools.convertJsonToNestMap(intentData);
                //将数据按类型存入，以方便
                setBundleData(bundle, map);
                //将所有数据作为json字符串一起存入
                bundle.putString(Config.BUNDLE_TRANSLATE_KEY,intentData.toString());
                intent.putExtra(Config.BUNDLE_DATA_KEY,bundle);
            }
            context.startActivity(intent);
        }
    }



    /**
     * 设置界面间携带的数据
     * @param intent 启动意图
     * @param map 携带的数据
     * @return
     */
    public static Intent setIntentData(Intent intent, Map<String, Object> map) {
        for (Map.Entry<String,Object> entry : map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value == null){
                continue;
            }

            if (value instanceof Integer) {
                intent.putExtra(key, (Integer)value);
            }else if(value instanceof Double){
                intent.putExtra(key, (Double) value);
            }else if (value instanceof Boolean) {
                intent.putExtra(key, (Boolean)value);
            } else if (value instanceof String) {
                intent.putExtra(key, value.toString());
            } else if (value instanceof Entity) {
                intent.putExtra(key, (Entity) value);
            } else if (value instanceof ArrayList) {
                ArrayList list = (ArrayList) value;
                if (list.size() > 0) {
                    if (list.get(0) instanceof String) {
                        intent.putStringArrayListExtra(key, list);
                    }else if (list.get(0) instanceof Character) {
                        intent.putCharSequenceArrayListExtra(key, list);
                    } else if (list.get(0) instanceof Integer) {
                        intent.putIntegerArrayListExtra(key, list);
                    }
                }
            }else if (value instanceof Serializable) {
                intent.putExtra(key, (Serializable) value);
            }
        }
        return intent;
    }


    /**
     * 设置界面间携带的数据
     * @param bundle bundle
     * @param map 携带的数据
     * @return
     */
    public static Bundle setBundleData(Bundle bundle, Map<String, Object> map) {
        for (Map.Entry<String,Object> entry : map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();

            if(value == null){
                continue;
            }

            if (value instanceof Integer) {
                bundle.putInt(key, (Integer)value);
            }else if(value instanceof Double){
                bundle.putDouble(key, (Double) value);
            }else if (value instanceof Boolean) {
                bundle.putBoolean(key, (Boolean)value);
            } else if (value instanceof String) {
                bundle.putString(key, value.toString());
            } else if (value instanceof Entity) {
                bundle.putSerializable(key, (Entity) value);
            } else if (value instanceof ArrayList) {
                ArrayList list = (ArrayList) value;
                if (list.size() > 0) {
                    if (list.get(0) instanceof String) {
                        bundle.putStringArrayList(key, list);
                    }else if (list.get(0) instanceof Character) {
                        bundle.putCharSequenceArrayList(key, list);
                    } else if (list.get(0) instanceof Integer) {
                        bundle.putIntegerArrayList(key, list);
                    }
                }
            }else if (value instanceof Serializable) {
                bundle.putSerializable(key, (Serializable) value);
            }
        }
        return bundle;
    }

}