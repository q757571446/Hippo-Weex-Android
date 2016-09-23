package com.example.hippoweex.ioc.dagger.activity.fragment.user;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by kevinhao on 16/7/19.
 * 用于描述app的用户生命周期,提供用户实例
 * 在用户登录时创建,而当app中用户退出时得以销毁
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface UserScope {
}
