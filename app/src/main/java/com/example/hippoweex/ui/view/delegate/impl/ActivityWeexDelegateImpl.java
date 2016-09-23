package com.example.hippoweex.ui.view.delegate.impl;

import android.os.Bundle;

import com.example.hippoweex.ui.view.delegate.ActivityWeexDelegate;
import com.example.hippoweex.ui.view.delegate.BaseWeexDelegateCallback;

/**
 * Created by dell on 2016/9/21.
 */
public class ActivityWeexDelegateImpl implements ActivityWeexDelegate{
    private BaseWeexDelegateCallback delegateCallback;

    WeexInternalDelegate internalDelegate;

    public ActivityWeexDelegateImpl(BaseWeexDelegateCallback delegateCallback) {
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
    public void onCreate(Bundle bundle) {
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
    public void onRestart() {
    }

    @Override
    public void onContentChanged() {
        getInternalDelegate().attachView();
        getInternalDelegate().setNavBarSetter();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean onBackPressed() {
        return getInternalDelegate().getInstance().onActivityBack();
    }
}
