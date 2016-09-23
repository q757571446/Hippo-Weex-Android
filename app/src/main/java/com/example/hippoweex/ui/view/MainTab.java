package com.example.hippoweex.ui.view;

import android.content.Context;
import android.os.Bundle;

import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.fragment.tab.MainTabFragment_1;
import com.example.hippoweex.ui.view.fragment.tab.MainTabFragment_2;
import com.example.hippoweex.ui.view.fragment.tab.MainTabFragment_3;
import com.example.hippoweex.ui.view.fragment.tab.MainTabFragment_4;
import com.example.hippoweex.ui.view.fragment.tab.MainTabFragment_5;

/**
 * Created by dell on 2016/9/20.
 */
public enum MainTab {
    MAIN_TAB_RECOMMENDATION(0, R.string.main_tab_1, R.drawable.tab_icon_1,MainTabFragment_1.class),
    MAIN_TAB_INFORMATION(1, R.string.main_tab_2,  R.drawable.tab_icon_2,MainTabFragment_2.class),
    MAIN_TAB_CLOUDSEARCH(2, R.string.main_tab_3, R.drawable.tab_icon_3, MainTabFragment_3.class),
    MAIN_TAB_GROUP(3,R.string.main_tab_4,  R.drawable.tab_icon_4,MainTabFragment_4.class),
    MAIN_TAB_MY(4,R.string.main_tab_5,  R.drawable.tab_icon_4,MainTabFragment_5.class);


    private Class<?> clz;
    private int idx;
    private int resIcon;
    private int resName;
    private Bundle bundle;

    private MainTab(int index, int resName, int resIcon, Class<?> clz) {
        this.idx = index;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
        this.bundle = new Bundle();
    }

    public Bundle getBundle() {
        return bundle;
    }

    public static MainTab getPageByResName(Context context, String resName){
        for (MainTab page : values()) {
            if (context.getString(page.getResName()).equals(resName)) {
                return page;
            }
        }
        return null;
    }

    /**
     * 判断是否tab页面有变动，有变动重置class文件
     * @return
     */
    public static boolean match(String tabUri){
        for(MainTab tab: values()){
            //遍历容器，看是否和动态指定的匹配，匹配则使用native，不匹配则使用weex的
            if(tabUri==null || tabUri.contains(tab.getClz().getSimpleName())){
                return true;
            }
        }
        //遍历完毕都没找到和tabUri匹配的
        return false;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }
}
