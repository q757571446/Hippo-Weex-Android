package com.example.hippoweex.ui.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.hippoweex.Navigator;
import com.example.hippoweex.ioc.HasComponent;
import com.example.hippoweex.ui.IBroadcastReg;
import com.example.hippoweex.ui.view.activity.BaseActivity;
import com.example.hippoweex.utils.KeyboardUtil;

import javax.inject.Inject;

/**
 * Created by dell on 2016/9/20.
 */
public abstract class BaseFragment extends SupportFragment implements IBroadcastReg{
    protected LayoutInflater mInflater;
    protected BaseActivity mContext;
    private OnFragmentInteractionListener mListener;
    @Inject
    protected Navigator navigator;

    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (BaseActivity) context;
        injectDependencies();

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //为了性能避免多次填充
        if (fragmentRootView == null) {
            registerBroadcast();
            return super.onCreateView(inflater, container,savedInstanceState);
        } else {
            ViewGroup group = (ViewGroup) fragmentRootView.getParent();
            if (group != null) {
                group.removeView(fragmentRootView);
            }
            return fragmentRootView;
        }
    }

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mInflater = inflater;
        return View.inflate(getActivity(), getLayoutId(), null);
    }


    @Override
    public void onDestroy() {
        unRegisterBroadcast();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mContext = null;
    }

    @Override
    public void registerBroadcast() {

    }

    @Override
    public void unRegisterBroadcast() {

    }

    /**
     * 注入Fragment对应的Component
     */
    protected void injectDependencies() {

    }

    /**
     * 获取所依附Activity的Component
     * @param componentType Activity对应的Component的class文件
     * @param <C>  Component类型
     * @return Component对象
     */
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    /**
     * 获取所依附Activity的启动意图
     * @return
     */
    protected Intent getIntent() {
        return getActivity().getIntent();
    }

    /**
     * 获取全局Window对象
     * @return Window
     */
    protected Window getWindow() {
        return getActivity().getWindow();
    }

    /**
     * Fragment借此向Activity通信
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 销毁当前界面
     */
    protected void finish() {
        if (getActivity() == null) {
            return ;
        }
        getActivity().finish();
    }

    public boolean onBackPressed() {
        //隐藏软键盘
        KeyboardUtil.hideSoftInputFromWindow(mContext, getRootView().getWindowToken());
        finish();
        return true;
    }
}
