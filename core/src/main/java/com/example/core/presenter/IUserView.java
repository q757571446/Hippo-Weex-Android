package com.example.core.presenter;

/**
 * Created by kevinhao on 16/7/26.
 * 配合UserScope使用,描述应用生命周期
 * 所有用户实例存在界面都应该具有一个基本功能,在token过期时跳转登录界面
 */
public interface IUserView extends ISimpleView{
    void navigateToLoginPage();
}
