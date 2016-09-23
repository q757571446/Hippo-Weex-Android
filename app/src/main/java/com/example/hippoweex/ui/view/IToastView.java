package com.example.hippoweex.ui.view;

/**
 * Created by dell on 2016/9/20.
 */
public interface IToastView {
    void showToast(int msgId);

    void showToast(int msgId,int duration);

    void showToast(String msg);

    void showToast(String msg,int duration);
}
