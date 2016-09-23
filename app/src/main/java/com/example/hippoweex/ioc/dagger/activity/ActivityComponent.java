package com.example.hippoweex.ioc.dagger.activity;

import android.app.Activity;

import dagger.Component;

/**
 * Created by Kevin on 2016/6/9.
 */
@PerActivity
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {
    Activity getAcitivity();
}
