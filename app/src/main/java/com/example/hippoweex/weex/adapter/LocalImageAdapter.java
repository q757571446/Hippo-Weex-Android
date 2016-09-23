package com.example.hippoweex.weex.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.example.hippoweex.weex.module.ResourceModule;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;

/**
 * Created by kevin on 16-8-1.z
 */
public class LocalImageAdapter implements IWXImgLoaderAdapter
{
    @Override
    public void setImage(final String url, final ImageView view, WXImageQuality quality, final WXImageStrategy strategy) {
        WXSDKManager.getInstance().postOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(view==null||view.getLayoutParams()==null){
                    return;
                }

                if (view.getLayoutParams().width <= 0 || view.getLayoutParams().height <= 0) {
                    return;
                }

                if (TextUtils.isEmpty(url)) {
                    return;
                }

                ResourceModule.loadImage(url, view,strategy);
            }
        },0);
    }
}
