package com.name.mvpboilerplate;

import android.app.Application;
import android.content.Context;

import com.name.mvpboilerplate.injection.component.ApplicationComponent;
import com.name.mvpboilerplate.injection.component.DaggerApplicationComponent;
import com.name.mvpboilerplate.injection.module.ApplicationModule;

import timber.log.Timber;

public class MvpBoilerplateApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static MvpBoilerplateApplication get(Context context) {
        return (MvpBoilerplateApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
