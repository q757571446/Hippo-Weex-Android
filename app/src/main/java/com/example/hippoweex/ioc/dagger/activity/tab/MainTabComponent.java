package com.example.hippoweex.ioc.dagger.activity.tab;


import com.example.hippoweex.MainActivity;
import com.example.hippoweex.ioc.dagger.AppComponent;
import com.example.hippoweex.ioc.dagger.activity.ActivityComponent;
import com.example.hippoweex.ioc.dagger.activity.ActivityModule;
import com.example.hippoweex.ioc.dagger.activity.PerActivity;
import com.example.hippoweex.ioc.dagger.activity.fragment.SimpleMvpComponent;
import com.example.hippoweex.ioc.dagger.activity.fragment.SimpleMvpModule;

import dagger.Component;

/**
 * Created by Kevin on 2016/6/9.
 */
@PerActivity
@Component(dependencies = {AppComponent.class}, modules = {MainTabModule.class, ActivityModule.class})
public interface MainTabComponent extends ActivityComponent {
    void inject(MainActivity mainActivity);

    SimpleMvpComponent plus(SimpleMvpModule module);
}
