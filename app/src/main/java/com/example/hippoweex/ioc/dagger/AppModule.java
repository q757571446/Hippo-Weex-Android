package com.example.hippoweex.ioc.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by dell on 2016/9/20.
 */
@Module
public class AppModule {
    Context mContext;

    public AppModule(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    @PerApplication
    public Context provideContext() {
        return mContext;
    }

}
