package com.example.hippoweex.ui.view;

import android.view.ViewGroup;

import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;

/**
 * Created by dell on 2016/9/21.
 */
public interface IWeexView {
    void showLoading();
    void hideLoading();
    void showError(String errorCode,String errorMsg);
    ViewGroup getContainer();

    CommonTitleBuilder getTitleBuilder();
}
