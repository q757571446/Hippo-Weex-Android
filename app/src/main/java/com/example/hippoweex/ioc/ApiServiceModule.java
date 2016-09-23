package com.example.hippoweex.ioc;

import android.content.Context;

import com.example.core.network.retrofit.RetrofitUtils;
import com.example.core.network.service.ApiService;
import com.example.hippoweex.ioc.dagger.PerApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiServiceModule {
    Context context;

    public ApiServiceModule(Context context) {
        this.context = context;
    }


    @Provides
    @PerApplication
    public ApiService provideApiService() {
        return RetrofitUtils.getInstance(context, ApiService.class);
    }

}