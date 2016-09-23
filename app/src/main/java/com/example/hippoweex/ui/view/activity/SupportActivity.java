package com.example.hippoweex.ui.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hippoweex.Navigator;
import com.example.hippoweex.ui.ActivityStack;
import com.example.hippoweex.ui.IActivitySkip;
import com.example.hippoweex.ui.IActivityStatus;
import com.example.hippoweex.ui.IBroadcastReg;
import com.example.hippoweex.ui.view.fragment.SupportFragment;

import java.util.Map;

/**
 * SupportActivity
 *  1. Activity之间基本的跳转方法及数据传递方法
 *  2. Fragment基本的初始化方法
 *  3. 记录栈信息，便于app退出
 *  4. 定义共有的初始化数据及控件的方法
 */
public abstract class SupportActivity extends AppCompatActivity implements IActivityStatus,IBroadcastReg,IActivitySkip {
    public int activityState = DESTROY;
    protected SupportFragment currentSupportFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityStack.create().addActivity(this);
        super.onCreate(savedInstanceState);
        setRootView();
        initializer();
        registerBroadcast();
    }

    private void initializer() {
        initData();
        initWidget();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void registerBroadcast() {

    }

    @Override
    public void unRegisterBroadcast() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = PAUSE;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = STOP;
    }

    @Override
    protected void onDestroy() {
        unRegisterBroadcast();
        activityState = DESTROY;
        currentSupportFragment = null;
        super.onDestroy();
        ActivityStack.create().finishActivity(this);
    }

    @Override
    public void skipActivity(Activity activity, Class<?> clazz) {
        showActivity(activity, clazz);
        finish();
    }

    @Override
    public void skipActivity(Activity activity, Intent intent) {
        showActivity(activity, intent);
        finish();
    }

    @Override
    public void skipActivity(Activity activity, Class<?> clazz, Map<String, Object> data) {
        showActivity(activity, clazz,data);
        finish();
    }

    @Override
    public void showActivity(Activity activity, Class<?> clazz) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);
        activity.startActivity(intent);
    }

    @Override
    public void showActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }
    @Override
    public void showActivity(Activity activity, Class<?> clazz, Map<String, Object> data) {
        Intent intent = new Intent();
        intent.setClass(activity, clazz);
        Navigator.setIntentData(intent, data);
        activity.startActivity(intent);
    }

    /**
     * 用Fragment替换视图
     *
     * @param resView        将要被替换掉的视图
     * @param targetFragment 用来替换的Fragment
     */
    protected void changeFragment(int resView, SupportFragment targetFragment) {
        if (targetFragment.equals(currentSupportFragment)) {
            return;
        }
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(resView, targetFragment, targetFragment.getClass()
                    .getName());
        }
        if (targetFragment.isHidden()) {
            transaction.show(targetFragment);
            targetFragment.onChange();
        }
        if (currentSupportFragment != null
                && currentSupportFragment.isVisible()) {
            transaction.hide(currentSupportFragment);
        }
        currentSupportFragment = targetFragment;
        transaction.commitAllowingStateLoss();
    }
}
