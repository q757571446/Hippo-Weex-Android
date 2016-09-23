package com.example.hippoweex;

import android.app.Application;

import com.example.hippoweex.ioc.ApiServiceModule;
import com.example.hippoweex.ioc.dagger.AppComponent;
import com.example.hippoweex.ioc.dagger.AppModule;
import com.example.hippoweex.ioc.dagger.DaggerAppComponent;
import com.example.hippoweex.weex.WeexManager;

/**
 * Created by dell on 2016/9/20.
 */
public class AppContext extends Application{
    private static AppContext context;
    private AppComponent mAppComponent;

    public AppContext() {
        context = this;
    }

    public static AppContext getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .apiServiceModule(new ApiServiceModule(this))
                .build();

        WeexManager.register(this);
    }

    public AppComponent getAppComponent(){
        return mAppComponent;
    }

}
