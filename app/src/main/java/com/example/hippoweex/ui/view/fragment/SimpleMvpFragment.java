package com.example.hippoweex.ui.view.fragment;

import android.os.Bundle;
import com.example.core.presenter.ISimpleView;
import com.example.core.presenter.SimpleMvpPresenter;
import com.example.hippoweex.MainActivity;
import com.example.hippoweex.ioc.dagger.activity.fragment.SimpleMvpComponent;
import com.example.hippoweex.ioc.dagger.activity.fragment.SimpleMvpModule;
import com.example.hippoweex.ioc.dagger.activity.simple.SimpleBackComponent;
import com.example.hippoweex.ioc.dagger.activity.tab.MainTabComponent;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Kevin on 2016/6/9.
 * 基类 MvpView  ——> MvpPresenter
 * 接口 ISimpleView ——> ISimplePresenter
 * 子类 SimpleMvpFragment ——》SimpleMvpPresenter——》SimpleMvpModule
 * 基类 MvpFragment——>MvpBasePresenter——>MvpModle
 * 处理复杂业务逻辑
 */
public abstract class SimpleMvpFragment<V extends ISimpleView, P extends SimpleMvpPresenter<V>> extends MvpFragment<V, P>
        implements ISimpleView {
    protected CompositeSubscription subscriptions;

    protected SimpleMvpComponent simpleMvpComponent;

    @Override
    public void showToast(String msg) {
        mContext.showToast(msg);
    }

    @Override
    public void showToast(int msgId) {
        mContext.showToast(getString(msgId));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (subscriptions != null && subscriptions.hasSubscriptions()) {
            subscriptions.unsubscribe();
        }
    }


    @Override
    protected void injectDependencies() {
        if (getActivity() instanceof MainActivity) {
            //一级页面
            simpleMvpComponent = this.getComponent(MainTabComponent.class)
                    .plus(new SimpleMvpModule());
        } else {
            //多级页面
            simpleMvpComponent = this.getComponent(SimpleBackComponent.class)
                    .plus(new SimpleMvpModule());
        }
    }

    protected SimpleMvpComponent getSimpleMvpComponent(){
        return simpleMvpComponent;
    }
}
