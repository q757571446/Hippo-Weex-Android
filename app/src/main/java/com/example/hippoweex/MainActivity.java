package com.example.hippoweex;

import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.core.manager.ClassManager;
import com.example.hippoweex.ioc.HasComponent;
import com.example.hippoweex.ioc.dagger.activity.ActivityModule;
import com.example.hippoweex.ioc.dagger.activity.tab.DaggerMainTabComponent;
import com.example.hippoweex.ioc.dagger.activity.tab.MainTabComponent;
import com.example.hippoweex.ioc.dagger.activity.tab.MainTabModule;
import com.example.hippoweex.ui.ActivityStack;
import com.example.hippoweex.ui.view.MainTab;
import com.example.hippoweex.ui.view.activity.BaseActivity;
import com.example.hippoweex.ui.view.fragment.BaseFragment;
import com.example.hippoweex.ui.widget.FragmentTabHost;
import com.example.hippoweex.ui.widget.toolbar.CommonTitleBuilder;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener,HasComponent<MainTabComponent>{
    @BindView(android.R.id.tabhost)
    FragmentTabHost mTabHost;

    @BindView(R.id.realtabcontent)
    FrameLayout container;

    private TabHost.TabSpec mTabSpec;
    private MainTabComponent mMainComponent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeTitleBar(CommonTitleBuilder builder) {
        super.initializeTitleBar(builder);

        builder.setTitleText("Playground");
    }

    @Override
    public void onBackPressed() {
        ActivityStack.create().appExit(this);
        super.onBackPressed();
    }

    @Override
    protected void injectDependencies() {
        super.injectDependencies();

        mMainComponent = DaggerMainTabComponent.builder().appComponent(getAppComponent())
                .mainTabModule(new MainTabModule())
                .activityModule(new ActivityModule(this))
                .build();
        mMainComponent.inject(this);
    }

    @Override
    public void initWidget() {
        super.initWidget();

        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }

        int length = MainTab.values().length;
        ArrayList<String> tabList = getIntent().getStringArrayListExtra("tab-list");


        for (int i=0;i<length;i++) {
            MainTab tab = MainTab.values()[i];

            mTabSpec = mTabHost.newTabSpec(getString(tab.getResName()));

            //填充tab布局
            View view = LayoutInflater.from(AppContext.getContext()).inflate(R.layout.tab_indicator, null);

            //设置icon
            TextView title = (TextView) view.findViewById(R.id.tab_title);

            title.setCompoundDrawablesWithIntrinsicBounds(0, tab.getResIcon() ,0,0);
            title.setText(getString(tab.getResName()));
            title.setGravity(Gravity.CENTER);

            mTabSpec.setIndicator(view);
            mTabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });

            if(tabList != null){
                String tabUri = tabList.get(i);
                if(!MainTab.match(tabUri)){
                    //不匹配，使用weex的
                    Uri parse = Uri.parse(tabUri);
                    tab.getBundle().putString("BUNDLE_KEY_ARGS_URI", parse.buildUpon().clearQuery().toString());
                    Class clazz = ClassManager.getClazz(getFragmentPath(parse));
                    tab.setClz(clazz);
                }
            }
            mTabHost.addTab(mTabSpec, tab.getClz(), tab.getBundle());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public MainTabComponent getComponent() {
        return mMainComponent;
    }
}
