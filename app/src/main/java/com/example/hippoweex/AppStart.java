package com.example.hippoweex;

import com.example.core.Config;
import com.example.hippoweex.ui.view.activity.WeexActivity;

/**
 * Created by dell on 2016/9/20.
 */
public class AppStart extends WeexActivity{
    @Override
    public String getIndexUrl() {
        return Config.WEEX_DEFAULT_PAGE;
    }
}
