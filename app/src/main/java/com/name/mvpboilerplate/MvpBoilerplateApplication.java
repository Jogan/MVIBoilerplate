package com.name.mvpboilerplate;

import android.app.Application;
import android.content.Context;
import com.name.mvpboilerplate.dagger.ApiModule;
import com.name.mvpboilerplate.dagger.ApplicationComponent;
import com.name.mvpboilerplate.dagger.ApplicationModule;
import com.name.mvpboilerplate.dagger.DaggerApplicationComponent;
import timber.log.Timber;

public class MvpBoilerplateApplication extends Application {

  ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    initComponent();
  }

  public static MvpBoilerplateApplication get(Context context) {
    return (MvpBoilerplateApplication) context.getApplicationContext();
  }

  public void initComponent() {
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .apiModule(new ApiModule())
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
