package com.example.hippoweex.ui.view;

import android.content.Context;
import android.net.Uri;

import com.example.hippoweex.R;
import com.example.hippoweex.ui.view.fragment.login.LoginFragment;
import com.example.hippoweex.weex.module.ResourceModule;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2016/9/20.
 */
public enum SimpleBackPage {
    LOGIN_FRAGMENT(0, R.string.main_tab_3, LoginFragment.class);

    private Class<?> clz;
    private int title;
    private int index;

    /**
     *
     * @param index 索引
     * @param title 标题栏内容
     * @param clz Fragment
     */
    private SimpleBackPage(int index, int title, Class<?> clz) {
        this.index = index;
        this.title = title;
        this.clz = clz;
    }


    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Class<?> getClz() {
        return clz;
    }

    public int getTitle() {
        return title;
    }

    public int getIndex() {
        return index;
    }

    /**
     * 查询Fragment
     * @param index 索引
     * @return 枚举对象
     */
    public static SimpleBackPage getPageByIndex(int index) {
        for (SimpleBackPage page : values()) {
            if (page.getIndex() == index) {
                return page;
            }
        }
        return null;
    }

    public Uri getPageUriWithTitle(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("title", context.getString(getTitle()));
        return ResourceModule.getClassUri(context,getClz(),map);
    }

    public Uri getPageUriWithoutTitle(Context context) {
        return ResourceModule.getClassUri(context,getClz(),null);
    }
}