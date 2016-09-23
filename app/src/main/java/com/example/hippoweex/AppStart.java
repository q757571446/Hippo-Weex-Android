package com.example.hippoweex;

import android.view.View;

import com.example.core.Config;
import com.example.hippoweex.ui.view.activity.WeexActivity;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;

/**
 * Created by dell on 2016/9/20.
 */
public class AppStart extends WeexActivity{

    @Override
    protected void initializeTitleBar(CommonTitleBuilder builder) {
        super.initializeTitleBar(builder);
        builder.build().setVisibility(View.GONE);
    }

    @Override
    public String getIndexUrl() {
        return Config.WEEX_DEFAULT_PAGE;
    }
}
