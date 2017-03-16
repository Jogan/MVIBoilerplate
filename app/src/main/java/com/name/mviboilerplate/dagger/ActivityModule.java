package com.name.mviboilerplate.dagger;

import android.app.Activity;

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
