package com.example.hippoweex.ui.view.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.example.hippoweex.R;
import com.example.hippoweex.adapter.TabAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TabFragment extends NativeFragment {
    @BindView(R.id.tab_title)
    TabLayout mTabLayout;

    @BindView(R.id.tab_content)
    ViewPager mContentPager;

    private FragmentPagerAdapter mTabAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    protected Fragment getFragment(String uri){
        Bundle bundle = new Bundle();
        bundle.putString("BUNDLE_KEY_ARGS_URI", uri);
        return Fragment.instantiate(mContext, WeexFragment.class.getName(), bundle);
    }

    @Override
    protected void initWidget(View view) {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabAdapter = new TabAdapter(getActivity().getSupportFragmentManager(),getTabList());
        mContentPager.setAdapter(mTabAdapter);
        mTabLayout.setupWithViewPager(mContentPager);
    }

    protected List<TabAdapter.TabInfo> getTabList(){
        List<TabAdapter.TabInfo> list = new ArrayList<>();

        ArrayList<String> tabUri = getArguments().getStringArrayList("tabUri");
        if(tabUri != null && tabUri.size() > 0){
            for (String uri : tabUri){
                Uri parse = Uri.parse(uri);
                String title = parse.getQueryParameter("title");

                if(!TextUtils.isEmpty(title)){
                    TabAdapter.TabInfo tabInfo = new TabAdapter.TabInfo(title, getFragment(uri));
                    list.add(tabInfo);
                }
            }
        }
        return list;
    };
}