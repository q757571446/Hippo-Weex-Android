package com.example.hippoweex.ui.view.delegate;

import com.example.hippoweex.ui.view.IWeexView;
import com.taobao.weex.WXSDKInstance;

import java.util.Map;

/**
 * Created by dell on 2016/9/20.
 */
public interface BaseWeexDelegateCallback {

    WXSDKInstance createInstance();

    WXSDKInstance getInstance();

    void setInstance(WXSDKInstance instance);

    /**
     * 可用通过setArguments传递Weex界面所指向的url
     * 子类中，也可以重写此方法指向目标url
     * @return 当前界面url
     */
    String getIndexUrl();

    /**
     * Weex页面初始化时的数据
     * @return 初始化数据Json形式
     */
    Map<String,Object> getInitData();

    String getPagerName();


    IWeexView getWeexView();
}
