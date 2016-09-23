package com.example.hippoweex.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by dell on 2016/9/20.
 */
public class KeyboardUtil {
    /**
     * 隐藏软键盘
     * @param context 上下文
     * @param token 绑定的token
     */
    public static void hideSoftInputFromWindow(Context context, IBinder token) {
        ((InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(
                        token, 0);
    }
}
