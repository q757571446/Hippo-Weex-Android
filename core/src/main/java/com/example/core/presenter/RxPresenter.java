package com.example.core.presenter;

import android.content.Context;

import com.example.core.network.service.ApiService;
import com.hannesdorfmann.mosby.mvp.MvpView;

import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kevinhao on 16/7/20.
 * solve the dispatch of exception
 */
public abstract class RxPresenter<V extends MvpView> extends SimpleMvpPresenter<V> {

    protected CompositeSubscription subscriptions;

    public RxPresenter(Context context, ApiService requestApi) {
        super(context, requestApi);
    }


    @Override
    public void attachView(V view) {
        super.attachView(view);

        subscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (subscriptions != null && subscriptions.hasSubscriptions()) {
            subscriptions.unsubscribe();
        }
    }


    /**
     * 解决请求完毕后界面销毁
     * @param <T>
     */
    public class ViewFilterFunc<T> implements Func1<T, Boolean> {
        @Override
        public Boolean call(T t) {
            return isViewAttached();
        }
    }

}
