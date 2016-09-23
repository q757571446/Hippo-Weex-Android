package com.example.hippoweex.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/8/10.
 */
public class TabAdapter extends FragmentPagerAdapter {
    private List<TabInfo> infos;

    public void setData(List<TabInfo> data) {
        this.infos = data;
    }

    public  List<TabInfo> getData(){
        return this.infos;
    }

    public void notifyDataSetChanged(List<TabInfo> list) {
        if(list == null){
            this.infos = new ArrayList<>();
        }
        this.infos = list;
        notifyDataSetChanged();
    }



    public static class TabInfo{
        String title;
        Fragment fragment;

        public TabInfo(){}

        public TabInfo(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }


    public TabAdapter(FragmentManager fm, List<TabInfo> infos) {
        super(fm);
        if(infos == null){
            infos = new ArrayList<>();
        }
        this.infos = infos;
    }



    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Fragment getItem(int position) {
        return infos.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return infos.get(position).getTitle();
    }

}
