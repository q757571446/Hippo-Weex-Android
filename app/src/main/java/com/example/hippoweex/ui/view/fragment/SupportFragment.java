package com.example.hippoweex.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * SupportFragment
 *  1. 填充布局
 *  2. 提供基本初始化方法
 */
public abstract class SupportFragment extends Fragment{

    protected View fragmentRootView;

    protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentRootView = inflaterView(inflater, container, savedInstanceState);
        return fragmentRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initWidget(fragmentRootView);
    }

    protected void initData() {
    }

    protected void initWidget(View root) {

    }

    protected View getRootView() {
        return fragmentRootView;
    }

    public void onChange() {

    }
}
