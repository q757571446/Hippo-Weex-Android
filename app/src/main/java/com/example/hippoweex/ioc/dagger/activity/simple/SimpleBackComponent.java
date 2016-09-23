package com.example.hippoweex.ioc.dagger.activity.simple;


import com.example.hippoweex.ioc.dagger.AppComponent;
import com.example.hippoweex.ioc.dagger.activity.ActivityComponent;
import com.example.hippoweex.ioc.dagger.activity.ActivityModule;
import com.example.hippoweex.ioc.dagger.activity.PerActivity;
import com.example.hippoweex.ioc.dagger.activity.fragment.SimpleMvpComponent;
import com.example.hippoweex.ioc.dagger.activity.fragment.SimpleMvpModule;
import com.example.hippoweex.ui.view.activity.SimpleBackActivity;

import dagger.Component;

/**
 * Created by Kevin on 2016/6/9.
 */
@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {SimpleBackModule.class, ActivityModule.class})
public interface SimpleBackComponent extends ActivityComponent {
    void inject(SimpleBackActivity simpleBackActivity);

    SimpleMvpComponent plus(SimpleMvpModule module);
}
