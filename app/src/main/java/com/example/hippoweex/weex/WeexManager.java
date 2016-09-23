package com.example.hippoweex.weex;

import android.app.Application;

import com.example.core.Config;
import com.example.hippoweex.weex.adapter.LocalImageAdapter;
import com.example.hippoweex.weex.adapter.NormalTitleAdapter;
import com.example.hippoweex.weex.adapter.PlayDebugAdapter;
import com.example.hippoweex.weex.adapter.RetrofitHttpAdapter;
import com.example.hippoweex.weex.component.WXCardView;
import com.example.hippoweex.weex.component.WXTabPage;
import com.example.hippoweex.weex.component.WXTreeView;
import com.example.hippoweex.weex.module.NavigatorModule;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

/**
 * Created by dell on 2016/9/20.
 */
public class WeexManager {

    public static void register(Application application){
        if(Config.DEBUG)
            initDebugEnvironment(true, Config.CURRENT_IP);

        WXSDKEngine.addCustomOptions("appName", application.getPackageName());
        WXSDKEngine.addCustomOptions("appGroup", "WXApp");
        WXSDKEngine.initialize(application,
                new InitConfig.Builder()
                        .setHttpAdapter(new RetrofitHttpAdapter(application))
                        .setImgAdapter(new LocalImageAdapter())
                        .setDebugAdapter(new PlayDebugAdapter())
                        .build()
        );

        WXSDKEngine.setActivityNavBarSetter(new NormalTitleAdapter(application));

        try {
//            WXSDKEngine.registerComponent("tab-pager",WXTabPager.class, true);
//            WXSDKEngine.registerComponent("tab-indicator", WXTabIndicator.class, true);
//            WXSDKEngine.registerComponent("tab-content", WXTabContent.class);
//            WXSDKEngine.registerDomObject("tab-content",WXTabContent.TabContentNode.class);

            WXSDKEngine.registerComponent("tab-page", WXTabPage.class);
            WXSDKEngine.registerComponent("card-view", WXCardView.class);
            WXSDKEngine.registerComponent("tree-view", WXTreeView.class);
            WXSDKEngine.registerModule("WeexNavigator", NavigatorModule.class);

        } catch (WXException e) {
            e.printStackTrace();
        }
    }

    private static void initDebugEnvironment(boolean enable, String host) {
        if (!"DEBUG_SERVER_HOST".equals(host)) {
            WXEnvironment.sDebugMode = enable;
            WXEnvironment.sDebugWsUrl = "ws://" + host + ":8088/debugProxy/native";
        }
    }
}
