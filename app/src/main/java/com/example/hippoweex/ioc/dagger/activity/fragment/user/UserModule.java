package com.example.hippoweex.ioc.dagger.activity.fragment.user;

import android.content.Context;

import com.example.core.manager.UserManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kevinhao on 16/7/26.
 * 用于提供用户实例----> TOKEN
 */
@Module
public class UserModule {

    @Provides
    @UserScope
    public UserManager.UserToken provideUserToken(Context context) {
        return UserManager.readUserToken(context);
    }
}
