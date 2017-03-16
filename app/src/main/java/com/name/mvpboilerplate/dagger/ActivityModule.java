package com.name.mvpboilerplate.dagger;

import android.app.Activity;
import android.content.Context;

import com.name.mvpboilerplate.dagger.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
@ActivityScope
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }
}
