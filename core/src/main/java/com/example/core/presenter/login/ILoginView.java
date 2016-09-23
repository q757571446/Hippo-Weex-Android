package com.example.core.presenter.login;


import com.example.core.presenter.ISimpleView;

public interface ILoginView extends ISimpleView {
    String getUsername();

    String getPassword();

    void setPassword(String password);

    void setLoginBtnEnable(boolean enable);

    void navigateToMainActivity();

    void setVericodePic(String picUrl);

    String getVerifyCode();
}
