package com.example.hippoweex.ioc.dagger.activity;

import android.app.Activity;
import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kevin on 2016/6/9.
 */
@PerActivity
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return activity;
    }


    @Provides
    @PerActivity
    public Picasso providePicasso(Context context) {
        return Picasso.with(context);
    }
}
