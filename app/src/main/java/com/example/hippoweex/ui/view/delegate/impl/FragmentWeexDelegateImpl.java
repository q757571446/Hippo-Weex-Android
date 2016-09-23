package com.example.hippoweex.ui.view.delegate.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.hippoweex.ui.view.delegate.BaseWeexDelegateCallback;
import com.example.hippoweex.ui.view.delegate.FragmentWeexDelegate;

/**
 * Created by dell on 2016/9/21.
 */
public class FragmentWeexDelegateImpl implements FragmentWeexDelegate{
    private BaseWeexDelegateCallback delegateCallback;
    WeexInternalDelegate internalDelegate;

    public FragmentWeexDelegateImpl(BaseWeexDelegateCallback delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        this.delegateCallback = delegateCallback;
    }

    protected WeexInternalDelegate getInternalDelegate() {
        if (internalDelegate == null) {
            internalDelegate = new WeexInternalDelegate(delegateCallback);
        }

        return internalDelegate;
    }

    @Override
    public void onCreate(Bundle saved) {
        getInternalDelegate().destroyInstance();
        getInternalDelegate().createInstance();
        getInternalDelegate().getInstance().onActivityCreate();
    }

    @Override
    public void onDestroy() {
        getInternalDelegate().getInstance().onActivityDestroy();
        getInternalDelegate().destroyInstance();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getInternalDelegate().attachView();
        getInternalDelegate().setNavBarSetter();
    }

    @Override
    public void onDestroyView() {
        getInternalDelegate().detachView();
    }

    @Override
    public void onPause() {
        getInternalDelegate().getInstance().onActivityPause();
    }

    @Override
    public void onResume() {
        getInternalDelegate().getInstance().onActivityResume();
    }

    @Override
    public void onStart() {
        getInternalDelegate().getInstance().onActivityStart();
    }

    @Override
    public void onStop() {
        getInternalDelegate().getInstance().onActivityStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    }

    @Override
    public void onAttach(Activity activity) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public boolean onBackPressed() {
        return getInternalDelegate().getInstance().onActivityBack();
    }
}
