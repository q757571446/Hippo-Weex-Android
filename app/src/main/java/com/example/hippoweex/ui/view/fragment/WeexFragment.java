package com.example.hippoweex.ui.view.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.Config;
import com.example.core.utils.GsonTools;
import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.IWeexView;
import com.example.hippoweex.ui.view.delegate.BaseWeexDelegateCallback;
import com.example.hippoweex.ui.view.delegate.FragmentWeexDelegate;
import com.example.hippoweex.ui.view.delegate.impl.FragmentWeexDelegateImpl;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/9/20.
 */
public class WeexFragment extends NativeFragment implements BaseWeexDelegateCallback,IWeexView{
    private BroadcastReceiver mReloadReceiver;
    FragmentWeexDelegate weexDelegate;
    private WXSDKInstance instance;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_weex;
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
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReloadReceiver,new IntentFilter(WXSDKEngine.JS_FRAMEWORK_RELOAD));
    }

    @Override
    public void unRegisterBroadcast() {
        super.unRegisterBroadcast();

        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReloadReceiver);
    }



    @NonNull
    protected FragmentWeexDelegate getWeexDelegate() {
        if (weexDelegate == null) {
            weexDelegate = new FragmentWeexDelegateImpl(this);
        }
        return weexDelegate;
    }

    @Override
    public WXSDKInstance createInstance() {
        return new WXSDKInstance(getActivity());
    }


    @Override
    public WXSDKInstance getInstance() {
        return instance;
    }

    @Override
    public void setInstance(WXSDKInstance instance) {
        this.instance = instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public ViewGroup getContainer() {
        return (ViewGroup) getRootView().findViewById(R.id.index_container);
    }

    @Override
    public CommonTitleBuilder getTitleBuilder() {
        return getTitleBar();
    }


    @Override
    public IWeexView getWeexView() {
        return this;
    }

    @Override
    public String getIndexUrl() {
        if(getArguments() != null&& !TextUtils.isEmpty(getArguments().getString(Config.BUNDLE_PAGE_KEY))){
            return getArguments().getString(Config.BUNDLE_PAGE_KEY);
        }else{
            return Config.WEEX_DEFAULT_PAGE;
        }
    }

    @Override
    public final Map<String,Object> getInitData() {
        Map<String, Object> weexData = getWeexData();
        if(getArguments() != null&& !TextUtils.isEmpty(getArguments().getString(Config.BUNDLE_TRANSLATE_KEY))){
            String initData = getArguments().getString(Config.BUNDLE_TRANSLATE_KEY);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWeexDelegate().onCreate(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        getWeexDelegate().onDestroy();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWeexDelegate().onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getWeexDelegate().onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        getWeexDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWeexDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getWeexDelegate().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getWeexDelegate().onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getWeexDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        getWeexDelegate().onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getWeexDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getWeexDelegate().onSaveInstanceState(outState);
    }

    @Override
    public boolean onBackPressed() {
        return getWeexDelegate().onBackPressed();
    }

    @Override
    public void showLoading() {
        //展示进度条
        getRootView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        getRootView().findViewById(R.id.tip).setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        //隐藏进度条
        getRootView().findViewById(R.id.progressBar).setVisibility(View.GONE);
        getRootView().findViewById(R.id.tip).setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorCode,String errorMsg) {
        //展示错误界面
        TextView tip = (TextView) getRootView().findViewById(R.id.tip);
        tip.setVisibility(View.VISIBLE);
        tip.setText(errorCode+">>>>"+errorMsg);
    }
}
