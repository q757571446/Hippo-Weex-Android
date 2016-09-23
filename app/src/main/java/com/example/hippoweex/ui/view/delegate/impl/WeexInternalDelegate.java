package com.example.hippoweex.ui.view.delegate.impl;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.utils.GsonTools;
import com.example.hippoweex.ui.view.IWeexView;
import com.example.hippoweex.ui.view.delegate.BaseWeexDelegateCallback;
import com.example.hippoweex.weex.adapter.NormalTitleAdapter;
import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.appfram.navigator.IActivityNavBarSetter;
import com.taobao.weex.common.WXRenderStrategy;
import com.taobao.weex.utils.WXFileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/9/21.
 */
public class WeexInternalDelegate implements IWXRenderListener, Runnable {
    private final BaseWeexDelegateCallback delegateCallback;

    public WeexInternalDelegate(BaseWeexDelegateCallback delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        this.delegateCallback = delegateCallback;
    }


    void createInstance(){
        WXSDKInstance instance = delegateCallback.getInstance();
        if (instance == null) {
            instance = delegateCallback.createInstance();
        }

        if (instance == null) {
            throw new NullPointerException("WeexInstance is null! Do you return null in createInstance()?");
        }

        instance.registerRenderListener(this);
        delegateCallback.setInstance(instance);
    }

    void destroyInstance(){
        WXSDKInstance instance = delegateCallback.getInstance();
        if(instance!=null){
            instance.registerRenderListener(null);
            instance.destroy();
        }
    }

    WXSDKInstance getInstance(){
        WXSDKInstance instance = delegateCallback.getInstance();
        if (instance == null) {
            throw new NullPointerException("WeexInstance is null! Do you return null in createInstance()?");
        }
        return instance;
    }

    private IWeexView getWeexView(){
        IWeexView weexView = delegateCallback.getWeexView();
        if (weexView == null) {
            throw new NullPointerException("Do you return null in getWeexView()?");
        }
        return weexView;
    }

    private String getInitData(){
        Map<String, Object> initData = delegateCallback.getInitData();
        return GsonTools.convertMapToString(initData);
    }

    private ViewGroup getContainer(){
        ViewGroup container = getWeexView().getContainer();
        if(container == null){
            throw new NullPointerException("WeexContainer is null!");
        }
        return container;
    }



    @Override
    public void run() {
        String path = delegateCallback.getIndexUrl();
        int measuredWidth = getContainer().getMeasuredWidth();
        int measuredHeight = getContainer().getMeasuredHeight();
        //发送渲染请求
        renderPager(path,measuredWidth,measuredHeight);
    }

    void attachView() {
        getContainer().post(this);
    }

    void detachView() {
    }

    void setNavBarSetter(){
        WXSDKEngine.setActivityNavBarSetter(getTitleAdapter());
    }

    private NormalTitleAdapter getTitleAdapter(){
        IActivityNavBarSetter activityNavBarSetter = WXSDKEngine.getActivityNavBarSetter();
        if (activityNavBarSetter == null || !(activityNavBarSetter instanceof NormalTitleAdapter)){
            return new NormalTitleAdapter(getInstance().getContext()).setTitleBuilder(getWeexView().getTitleBuilder());
        }else{
            return((NormalTitleAdapter) activityNavBarSetter).setTitleBuilder(getWeexView().getTitleBuilder());
        }
    }

    private void renderPager(String path, int containerWidth, int containerHeight){
        getWeexView().showLoading();

        String pageName = delegateCallback.getPagerName();
        Map<String, Object> options = new HashMap<>();
        options.put(WXSDKInstance.BUNDLE_URL, path);
        if(path.startsWith("http")){
            renderPagerFromNet(pageName,path,options, getInitData(),containerWidth,containerHeight);
        }else if(path.startsWith("file")){
            Uri uri = Uri.parse(path);
            String authority = uri.getEncodedAuthority();
            if(authority.equals("assets")){
                renderPagerFromAsset(pageName,uri,options,getInitData(),containerWidth,containerHeight);
            }else{
                renderPagerFromFile(pageName,path,options,getInitData(),containerWidth,containerHeight);
            }
        }
    }

    private void renderPagerFromNet(String pageName, String url, Map<String, Object> options, String jsonInitData, int containerWidth, int containerHeight){
        getInstance().renderByUrl(pageName,url,options,jsonInitData,containerWidth,containerHeight, WXRenderStrategy.APPEND_ASYNC);
    }

    private void renderPagerFromFile(String pageName, String path, Map<String, Object> options, String jsonInitData, int containerWidth, int containerHeight){
        //// TODO: 2016/9/21 需要做文件流的读取 
    }

    private void renderPagerFromAsset(String pageName, Uri uri, Map<String, Object> options, String jsonInitData, int containerWidth, int containerHeight){
        List<String> pathSegments = uri.getPathSegments();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<pathSegments.size(); i++){
            String p = pathSegments.get(i);
            if(i != pathSegments.size() -1){
                sb.append(p).append("/");
            }else{
                sb.append(p);
            }
            String assetPath = sb.toString();
            Context context = getInstance().getContext();
            String template = WXFileUtils.loadAsset(assetPath, context);

            getInstance().render(pageName,template,options,jsonInitData,containerWidth,containerHeight,WXRenderStrategy.APPEND_ASYNC);
        }
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        getWeexView().hideLoading();
        getContainer().removeAllViews();
        getContainer().addView(view);
    }

    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        getWeexView().hideLoading();
    }

    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        getWeexView().hideLoading();
    }

    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        getWeexView().hideLoading();
        getWeexView().showError(errCode,msg);
    }

}
