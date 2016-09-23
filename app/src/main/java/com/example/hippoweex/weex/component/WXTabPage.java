package com.example.hippoweex.weex.component;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.example.hippoweex.R;
import com.example.hippoweex.adapter.TabAdapter;
import com.example.hippoweex.ui.view.activity.BaseActivity;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2016/8/12.
 */
public class  WXTabPage extends WXComponent {

    private TabLayout mTabLayout;
    private ViewPager mContentPager;
    protected AppCompatActivity mAttachActivity;
    private TabAdapter mTabAdapter;

    private int otherHeight;
    private ViewTreeObserver vto;
    private int normalTextColor;
    private int selectedTextColor;

    public WXTabPage(WXSDKInstance instance, WXDomObject dom, WXVContainer parent, boolean isLazy) {
        super(instance, dom, parent, isLazy);
    }

    @Override
    protected void initView() {
        if(mContext instanceof AppCompatActivity){
            mAttachActivity = (AppCompatActivity) mContext;
        }else{
            throw new IllegalArgumentException("WXTabPage cannot attach to activity" + mContext);
        }
        mHost = LayoutInflater.from(mContext).inflate(R.layout.fragment_tab, null);
        mTabLayout = (TabLayout) mHost.findViewById(R.id.tab_title);
        mContentPager = (ViewPager) mHost.findViewById(R.id.tab_content);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabAdapter = new TabAdapter(mAttachActivity.getSupportFragmentManager(), null);
        mContentPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mContentPager);
    }


    @Override
    public LinearLayout getRealView() {
        return (LinearLayout) mHost;
    }


    /**
     * Measure the size of the recyclerView.
     *
     * @param width  the expected width
     * @param height the expected height
     * @return the result of measurement
     */
    @Override
    protected MeasureOutput measure(int width, int height) {
        int screenH = WXViewUtils.getScreenHeight(WXEnvironment.sApplication);
        int weexH = WXViewUtils.getWeexHeight(mInstanceId);
        int outHeight = height > (weexH >= screenH ? screenH : weexH) ? weexH - mAbsoluteY : height;
        if (outHeight == 0){
            outHeight = weexH - mAbsoluteY;
        }
        return super.measure(width, outHeight);
    }

    /**
     * 暂时不支持高度和文字修改，因为TabLayout没提供相应方法
     * {
     *     tab-height:
     *     tab-background:
     *     indicator-color:
     *     text-color:
     *     selected-text-color:
     * }
     * @param styles
     */
    @WXComponentProp(name= "tabStyle")
    public void setTabStyle(Map<String,String> styles){
//        String tabHeight = styles.get("tab-height");
        String tabBackground = styles.get("tab-background");
        String indicatorColor = styles.get("indicator-color");
        String indicatorHeight = styles.get("indicator-height");
        String textColor = styles.get("text-color");
//        String textSize = styles.get("text-size");
        String seleTextColor = styles.get("selected-text-color");

        if(!TextUtils.isEmpty(tabBackground)){
            mTabLayout.setBackgroundColor(Color.parseColor(tabBackground));
        }
        if(!TextUtils.isEmpty(indicatorColor)){
            mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(indicatorColor));
        }
        if(!TextUtils.isEmpty(indicatorHeight)){
            mTabLayout.setSelectedTabIndicatorHeight(Integer.valueOf(indicatorHeight));
        }
        if(!TextUtils.isEmpty(textColor)){
            normalTextColor = Color.parseColor(textColor);
        }else{
            normalTextColor = Color.parseColor("#212121");
        }
        if(!TextUtils.isEmpty(seleTextColor)){
            selectedTextColor = Color.parseColor(seleTextColor);
        }else{
            selectedTextColor = Color.parseColor("#f39c12");
        }
        mTabLayout.setTabTextColors(normalTextColor, selectedTextColor);
    }


    /**
     * tab-items: [
     http://www.baidu.com?title=标题,
     http://www.baidu.com?title=标题,
     http://www.baidu.com?title=标题,
     http://www.baidu.com?title=标题,
     * ]
     */
    @WXComponentProp(name= "pageContents")
    public void setContent(List<String> tabUri){
        List<TabAdapter.TabInfo> list = new ArrayList<>();

        if(tabUri != null && tabUri.size() > 0){
            for (String uri : tabUri){
                Uri parse = Uri.parse(uri);
                String title = parse.getQueryParameter("title");

                if(!TextUtils.isEmpty(title)){
                    TabAdapter.TabInfo tabInfo = new TabAdapter.TabInfo(title, getFragment(parse));
                    list.add(tabInfo);
                }
            }
        }
        notifyDataChanged(list);
    }

    public void notifyDataChanged(List<TabAdapter.TabInfo> list){
        if(mTabAdapter == null){
            mTabAdapter = new TabAdapter(mAttachActivity.getSupportFragmentManager(), list);
        }else{
            mTabAdapter.notifyDataSetChanged(list);
        }
    }

    private Fragment getFragment(Uri uri){
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_KEY_ARGS_URI", uri.toString());
        return Fragment.instantiate(mContext, BaseActivity.getFragmentPath(uri),bundle);
    }
}
