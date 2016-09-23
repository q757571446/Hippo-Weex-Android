package com.example.hippoweex.ui.view.fragment;


import com.example.core.presenter.IUserView;
import com.example.core.presenter.SimpleMvpPresenter;
import com.example.hippoweex.ioc.dagger.activity.fragment.user.UserComponent;
import com.example.hippoweex.ioc.dagger.activity.fragment.user.UserModule;
import com.example.hippoweex.ui.view.SimpleBackPage;

/**
 * Created by kevinhao on 16/7/26.
 * 与UserScope相对应的Fragment, 持有User实例, 并能够在token过期时,跳转登录界面
 * 所以可以认为TokenFragment的所有下属子类是UserComponent的
 * notes : subcomponent of SimpleBackCOmponent
 */
public abstract class UserFragment<V extends IUserView, P extends SimpleMvpPresenter<V>> extends SimpleMvpFragment<V, P>
        implements IUserView {

    @Override
    public void navigateToLoginPage() {
        //TODO 暂且只跳到登录,后期需求可能更改登录成功还要跳转到目标页
        navigator.pushSimpleBackPage(mContext, SimpleBackPage.LOGIN_FRAGMENT);
    }

    protected UserComponent getUserComponent() {
        return getSimpleMvpComponent().userComponent(new UserModule());
    }
}
