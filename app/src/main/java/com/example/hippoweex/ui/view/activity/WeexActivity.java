package com.example.hippoweex.ui.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.Config;
import com.example.core.utils.GsonTools;
import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.IWeexView;
import com.example.hippoweex.ui.view.delegate.ActivityWeexDelegate;
import com.example.hippoweex.ui.view.delegate.BaseWeexDelegateCallback;
import com.example.hippoweex.ui.view.delegate.impl.ActivityWeexDelegateImpl;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/9/21.
 */
public class WeexActivity extends BaseActivity implements BaseWeexDelegateCallback, IWeexView{
    private WXSDKInstance instance;
    ActivityWeexDelegate weexDelegate;
    private BroadcastReceiver mReloadReceiver;

    @NonNull
    protected ActivityWeexDelegate getWeexDelegate() {
        if (weexDelegate == null) {
            weexDelegate = new ActivityWeexDelegateImpl(this);
        }
        return weexDelegate;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_weex;
    }

    @Override
    public WXSDKInstance createInstance() {
        return new WXSDKInstance(this);
    }

    @Override
    public WXSDKInstance getInstance() {
        return instance;
    }

    @Override
    public void setInstance(WXSDKInstance instance) {
        this.instance = instance;
    }

    @Override
    public String getIndexUrl() {
        if(!TextUtils.isEmpty(getIntent().getStringExtra(Config.BUNDLE_PAGE_KEY))){
            return getIntent().getStringExtra(Config.BUNDLE_PAGE_KEY);
        }else{
            return Config.WEEX_DEFAULT_PAGE;
        }
    }

    @Override
    public final Map<String,Object> getInitData() {
        Map<String, Object> weexData = getWeexData();
        Bundle bundle = getIntent().getBundleExtra(Config.BUNDLE_DATA_KEY);
        if(bundle != null&& !TextUtils.isEmpty(bundle.getString(Config.BUNDLE_TRANSLATE_KEY))){
            String initData = bundle.getString(Config.BUNDLE_TRANSLATE_KEY);
            Map<String, Object> map = GsonTools.convertJsonToNestMap(initData);
            weexData.putAll(map);
        }
        return weexData;
    }

    /**
     * 子类重写此方法向Weex界面提交初始化数据
     * @return
     */
    protected Map<String,Object> getWeexData(){
        return new HashMap<>();
    }


    @Override
    public String getPagerName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public IWeexView getWeexView() {
        return this;
    }

    @Override
    public void showLoading() {
        //展示进度条
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.tip).setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        //隐藏进度条
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        findViewById(R.id.tip).setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorCode,String errorMsg) {
        //展示错误界面
        TextView tip = (TextView)findViewById(R.id.tip);
        tip.setVisibility(View.VISIBLE);
        tip.setText(errorCode+">>>>"+errorMsg);
    }

    @Override
    public ViewGroup getContainer() {
        return (ViewGroup) findViewById(R.id.index_container);
    }

    @Override
    public CommonTitleBuilder getTitleBuilder() {
        return getTitleBar();
    }

    @Override
    public void registerBroadcast() {
        super.registerBroadcast();

        mReloadReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //重新刷新数据
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mReloadReceiver,new IntentFilter(WXSDKEngine.JS_FRAMEWORK_RELOAD));
    }

    @Override
    public void unRegisterBroadcast() {
        super.unRegisterBroadcast();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReloadReceiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWeexDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWeexDelegate().onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWeexDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWeexDelegate().onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWeexDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getWeexDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWeexDelegate().onRestart();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getWeexDelegate().onContentChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getWeexDelegate().onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        getWeexDelegate().onBackPressed();
        super.onBackPressed();
    }
}
