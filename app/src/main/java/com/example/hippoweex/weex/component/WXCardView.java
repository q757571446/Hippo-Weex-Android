package com.example.hippoweex.weex.component;

import android.support.v7.widget.CardView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * Created by dell on 2016/8/17.
 */
public class WXCardView extends WXVContainer {

    public WXCardView(WXSDKInstance instance, WXDomObject node, WXVContainer parent, boolean lazy) {
        super(instance, node, parent, lazy);
    }

    @Override
    protected void initView() {
        if(mContext!=null) {
            mHost = new CardView(mContext);
        }
    }

    @Override
    public CardView getView() {
        return (CardView) super.getView();
    }

    @WXComponentProp(name="elevation")
    public void setElevation(int elevation){
        getView().setCardElevation(elevation);
    }
}
