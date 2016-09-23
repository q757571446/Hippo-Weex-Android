package com.example.core.manager;


import android.content.Context;
import android.text.TextUtils;

import com.example.core.Config;
import com.example.core.utils.PreferenceHelper;
import com.example.core.utils.StringHelper;

import org.json.JSONObject;

/**
 * Created by Kevin on 2016/6/10.
 * 负责用户实例的维护类
 * 15505505935
 */
public class UserManager {
    public static final UserToken userToken = new UserToken();

    public static boolean isLogin(Context context) {
        return !StringHelper.isEmpty(readUserToken(context).token);
    }

    /**
     * 写入用户token
     * @param context
     * @return
     */
    public static void saveUserToken(final Context context, JSONObject jsonObject) {
        //TODO 写入需要涉及加密操作
        //写入内存
        String token = jsonObject.optString("token");
        String openId = jsonObject.optString("openId");
        UserToken.token = token;
        UserToken.uid = openId;

        //写入文件
        PreferenceHelper.write(context, Config.TokenFile, "UID", openId);
        PreferenceHelper.write(context, Config.TokenFile, "TOKEN", token);

    }

    /**
     * 写入用户token
     * @param context
     * @return
     */
    public static void saveUserToken(final Context context, String uid, String token) {
        //TODO 写入需要涉及加密操作
        //写入内存
        UserToken.token = token;
        UserToken.uid = uid;

        //写入文件
        PreferenceHelper.write(context, Config.TokenFile, "UID", uid);
        PreferenceHelper.write(context, Config.TokenFile, "TOKEN", token);

    }

    /**
     * 提供用户token
     * @param context
     * @return
     */
    public static UserToken readUserToken(final Context context) {
        //TODO 读取需要涉及解密操作
        if (TextUtils.isEmpty(UserToken.token)) {
            //内存不存在——》本地读取token
            UserToken.token = PreferenceHelper.readString(context, Config.TokenFile, "TOKEN","");
            UserToken.uid = PreferenceHelper.readString(context, Config.TokenFile, "UID","");
        }


        return userToken;
    }


    public static class UserToken {
        public static String uid;
        public static String token;

        @Override
        public String toString() {
            return token;
        }
    }
}
