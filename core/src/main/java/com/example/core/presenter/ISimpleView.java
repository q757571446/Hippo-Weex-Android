package com.example.core.presenter;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Kevin on 2016/6/9.
 * 具有通用展示土司基本对话框功能
 */
public interface ISimpleView extends MvpView {
    void showToast(String msg);

    void showToast(int msgId);
}
