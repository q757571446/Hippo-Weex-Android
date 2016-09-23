package com.example.core.presenter;

import android.content.Context;

import com.example.core.network.service.ApiService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by kevin on 16-7-31.
 */
public class SimpleMvpPresenter<V extends MvpView> extends MvpBasePresenter<V>{
    protected final Context context;
    protected final ApiService requestApi;

    public SimpleMvpPresenter(Context context, ApiService requestApi){
        this.context = context;
        this.requestApi = requestApi;
    }
}
