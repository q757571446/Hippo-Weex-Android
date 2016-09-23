package com.example.hippoweex.ui.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.example.core.Config;
import com.example.hippoweex.R;
import com.example.hippoweex.ioc.HasComponent;
import com.example.hippoweex.ioc.dagger.activity.ActivityModule;
import com.example.hippoweex.ioc.dagger.activity.simple.DaggerSimpleBackComponent;
import com.example.hippoweex.ioc.dagger.activity.simple.SimpleBackComponent;
import com.example.hippoweex.ioc.dagger.activity.simple.SimpleBackModule;
import com.example.hippoweex.ui.view.fragment.BaseFragment;
import com.example.hippoweex.ui.view.fragment.SupportFragment;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;

import java.lang.ref.WeakReference;

/**
 * Created by dell on 2016/9/20.
 */
public class SimpleBackActivity extends BaseActivity implements HasComponent<SimpleBackComponent>,BaseFragment.OnFragmentInteractionListener {
    private SimpleBackComponent mSimpleBackComponent;
    private WeakReference<Fragment> mFragment;
    private CloseReserver closeReserver;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simple_fragment;
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();

        mSimpleBackComponent = DaggerSimpleBackComponent.builder()
                .appComponent(getAppComponent())
                .activityModule(new ActivityModule(this))
                .simpleBackModule(new SimpleBackModule())
                .build();

        mSimpleBackComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        initFromIntent();
    }

    protected String getTitleFromIntent(){
        Uri data = getIntent().getData();
        return data.getQueryParameter("title");
    }

    protected SupportFragment getFragmentFromIntent(){
        Uri data = getIntent().getData();
        Bundle bundle = getIntent().getBundleExtra(Config.BUNDLE_DATA_KEY);
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(Config.BUNDLE_PAGE_KEY, data.buildUpon().clearQuery().toString());
        return (SupportFragment) SupportFragment.instantiate(this, getFragmentPath(data),bundle);
    }

    private void initFromIntent() {
        SupportFragment instantiate = getFragmentFromIntent();
        replaceContainer(instantiate);
    }

    @Override
    protected void initializeTitleBar(CommonTitleBuilder builder) {
        super.initializeTitleBar(builder);
        String title = getTitleFromIntent();
        builder.setTitleText(title)
                .setLeftImage(R.mipmap.icon_back)
                .setLeftItemClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
    }


    private void replaceContainer(SupportFragment fragment) {
        changeFragment(R.id.container, fragment);
        mFragment = new WeakReference<Fragment>(fragment);
    }

    @Override
    public void onBackPressed() {
        if ((mFragment != null) && (mFragment.get() != null) && (mFragment.get() instanceof BaseFragment)) {
            if (((BaseFragment) mFragment.get()).onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.ACTION_DOWN) && (mFragment.get() instanceof BaseFragment)) {
            ((BaseFragment) mFragment.get()).onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void registerBroadcast() {
        super.registerBroadcast();
        closeReserver = new CloseReserver();
        registerReceiver(closeReserver, new IntentFilter("close"));

    }

    @Override
    public void unRegisterBroadcast() {
        super.unRegisterBroadcast();
        unregisterReceiver(closeReserver);
    }

    @Override
    public SimpleBackComponent getComponent() {
        return mSimpleBackComponent;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class CloseReserver extends BroadcastReceiver {
        public void onReceive(Context paramContext, Intent intent) {
            finish();
        }
    }
}
