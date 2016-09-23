package com.example.hippoweex.ui;

import android.app.Activity;
import android.content.Intent;

import java.util.Map;

/**
 * Created by dell on 2016/9/20.
 */
public interface IActivitySkip {
    void skipActivity(Activity activity, Class<?> clazz);

    void skipActivity(Activity activity, Intent intent);

    void skipActivity(Activity activity, Class<?> clazz, Map<String,Object> data);

    void showActivity(Activity activity, Class<?> clazz);

    void showActivity(Activity activity, Intent intent);

    void showActivity(Activity activity, Class<?> clazz, Map<String,Object>  data);
}
