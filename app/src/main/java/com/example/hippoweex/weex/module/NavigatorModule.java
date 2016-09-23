package com.example.hippoweex.weex.module;

import android.app.Activity;
import android.text.TextUtils;

import com.example.hippoweex.Navigator;
import com.example.hippoweex.ui.view.SimpleBackPage;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXModuleAnno;

import org.json.JSONObject;

import java.util.List;

/**
 * pushPageByUrl
 * pushPageByName
 * pushPageByIndex
 */
public class NavigatorModule extends WXModule {

    private final static String URL = "url";
    private final static String INDEX = "index";
    private final static String MSG_SUCCESS = "SUCCESS";
    private final static String MSG_FAILED = "FAILED";
    /**
     * 跳转下一个页面，没有办法传递bean对象，传递八大类型和List
     * @param param
     *  {
     *      url: dest + title,
     *      sticky: false,
     *      translate:{
     *          int: 1,
     *          boolean：true,
     *          double: 1.0,
     *          float:1.0,
     *          String: hello world,
     *          ...
     *      }
     *  }
     * @param callbackId
     */
    @WXModuleAnno
    public void push(String param, final String callbackId) {
        if (!TextUtils.isEmpty(param)) {
            if (WXSDKEngine.getActivityNavBarSetter() != null) {
                //TODO 解析参数，传递标题信息
                if (WXSDKEngine.getActivityNavBarSetter().push(param)) {
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                    return;
                }
            }

            try {
                JSONObject jsonObject = new JSONObject(param);
                String url = jsonObject.optString(URL, "");
                if (!TextUtils.isEmpty(url)) {
                    Navigator.pushSimpleBackPage(mWXSDKInstance.getContext(), url, jsonObject.optJSONObject("translate"));

                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                }
            } catch (Exception e) {
                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                        MSG_FAILED);
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    /**
     * 进入主页，需要销毁所有二级页面
     * @param tabIndex 五个tab页路径
     * @param callbackId
     */
    @WXModuleAnno
    public void goHome(List<String> tabIndex, final String callbackId) {
        Navigator.pushMainActivity(mWXSDKInstance.getContext(),tabIndex);
    }

    /**
     * 跳转下一个页面, 此页面必须在SimpleBackPage中定义
     * @param param
     *  {
     *      index : 1;
     *      translate:{ //传递数据
     *          Integer: 1
     *          Boolean：true
     *      }
     *  }
     * @param callbackId
     */
    @WXModuleAnno
    public void pushSimpleBackPage(String param, final String callbackId) {

        if (!TextUtils.isEmpty(param)) {
            if (WXSDKEngine.getActivityNavBarSetter() != null) {
                //TODO 解析参数，传递标题信息
                if (WXSDKEngine.getActivityNavBarSetter().push(param)) {
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                    return;
                }
            }

            try {
                JSONObject jsonObject = new JSONObject(param);
                int index = jsonObject.optInt(INDEX);
                if (index != -1) {
                    JSONObject data = jsonObject.optJSONObject("translate");

                    Navigator.pushSimpleBackPage(WXEnvironment.getApplication(), SimpleBackPage.getPageByIndex(index),data);

                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                }
            } catch (Exception e) {
                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                        MSG_FAILED);
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    /**
     * 回退上一个页面
     * @param param json格式数据：包含字段
     *                 var params = {
     *                      resultCode:
     *                 }
     *
     * @param callbackId
     */
    @WXModuleAnno
    public void pop(String param, final String callbackId) {

        if (WXSDKEngine.getActivityNavBarSetter() != null) {
            if (WXSDKEngine.getActivityNavBarSetter().pop(param)) {
                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                        MSG_SUCCESS);
                return;
            }
        }

        if (mWXSDKInstance.getContext() instanceof Activity) {
            ((Activity) mWXSDKInstance.getContext()).finish();
        }
    }

    /**
     * @param param
     * @param callbackId
     */

    @WXModuleAnno
    public void setNavBarRightItem(String param, final String callbackId) {
        if (!TextUtils.isEmpty(param)) {
            if (WXSDKEngine.getActivityNavBarSetter() != null) {
                if (WXSDKEngine.getActivityNavBarSetter().setNavBarRightItem(param)) {
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                    return;
                }
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    @WXModuleAnno
    public void clearNavBarRightItem(String param, final String callbackId) {
        if (WXSDKEngine.getActivityNavBarSetter() != null) {
            if (WXSDKEngine.getActivityNavBarSetter().clearNavBarRightItem(param)) {
                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                        MSG_SUCCESS);
                return;
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    @WXModuleAnno
    public void setNavBarLeftItem(String param, final String callbackId) {

        if (!TextUtils.isEmpty(param)) {
            if (WXSDKEngine.getActivityNavBarSetter() != null) {
                if (WXSDKEngine.getActivityNavBarSetter().setNavBarLeftItem(param)) {
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                    return;
                }
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);

    }

    @WXModuleAnno
    public void clearNavBarLeftItem(String param, final String callbackId) {
        if (WXSDKEngine.getActivityNavBarSetter() != null) {
            if (WXSDKEngine.getActivityNavBarSetter().clearNavBarLeftItem(param)) {
                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                        MSG_SUCCESS);
                return;
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    @WXModuleAnno
    public void setNavBarMoreItem(String param, final String callbackId) {
        if (!TextUtils.isEmpty(param)) {
            if (WXSDKEngine.getActivityNavBarSetter() != null) {
                if (WXSDKEngine.getActivityNavBarSetter().setNavBarMoreItem(param)) {
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                    return;
                }
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    @WXModuleAnno
    public void clearNavBarMoreItem(String param, final String callbackId) {
        if (WXSDKEngine.getActivityNavBarSetter() != null) {
            if (WXSDKEngine.getActivityNavBarSetter().clearNavBarMoreItem(param)) {
                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                        MSG_SUCCESS);
                return;
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    @WXModuleAnno
    public void setNavBarTitle(String param, final String callbackId) {
        if (!TextUtils.isEmpty(param)) {
            if (WXSDKEngine.getActivityNavBarSetter() != null) {
                if (WXSDKEngine.getActivityNavBarSetter().setNavBarTitle(param)) {
                    WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                            MSG_SUCCESS);
                    return;
                }
            }
        }

        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
                MSG_FAILED);
    }

    @WXModuleAnno
    public void setNavBarLeftItemClickListener(final String callbackId){
//        if (!TextUtils.isEmpty(callbackId)) {
//            IActivityNavBarSetter activityNavBarSetter = WXSDKEngine.getActivityNavBarSetter();
//            if (activityNavBarSetter!= null && activityNavBarSetter instanceof NormalTitleAdapter) {
//                   ((NormalTitleAdapter) activityNavBarSetter).setNavBarLeftItemClickListener(new View.OnClickListener() {
//                       @Override
//                       public void onClick(View view) {
//                           WXBridgeManager.getInstance().fireEvent(mWXSDKInstance.getInstanceId(), mWXSDKInstance.getRootCom().getRef(), WXEventType.CLICK_LEFT_ITEM, null, null);
//                       }
//                   });
//                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
//                        MSG_SUCCESS);
//            }
//        }
//
//        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
//                MSG_FAILED);
    }

    @WXModuleAnno
    public void setNavBarRightItemClickListener(final String callbackId){
//        if (!TextUtils.isEmpty(callbackId)) {
//            IActivityNavBarSetter activityNavBarSetter = WXSDKEngine.getActivityNavBarSetter();
//            if (activityNavBarSetter!= null && activityNavBarSetter instanceof NormalTitleAdapter) {
//                ((NormalTitleAdapter) activityNavBarSetter).setNavBarRightItemClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        WXBridgeManager.getInstance().fireEvent(mWXSDKInstance.getInstanceId(), mWXSDKInstance.getRootCom().getRef(), WXEventType.CLICK_RIGHT_ITEM, null, null);
//                    }
//                });
//
//                WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
//                        MSG_SUCCESS);
//            }
//        }
//
//        WXBridgeManager.getInstance().callback(mWXSDKInstance.getInstanceId(), callbackId,
//                MSG_FAILED);
    }
}