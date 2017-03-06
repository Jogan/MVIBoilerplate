package com.name.mvpboilerplate;

import android.app.Application;
import android.content.Context;

import com.name.mvpboilerplate.dagger.component.ApplicationComponent;
import com.name.mvpboilerplate.dagger.component.DaggerApplicationComponent;
import com.name.mvpboilerplate.dagger.module.ApplicationModule;

import timber.log.Timber;

public class MvpBoilerplateApplication extends Application  {

    ApplicationComponent applicationComponent;

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
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
