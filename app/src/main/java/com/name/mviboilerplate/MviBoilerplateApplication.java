package com.name.mviboilerplate;

import android.app.Application;
import android.content.Context;
import com.name.mviboilerplate.dagger.ApiModule;
import com.name.mviboilerplate.dagger.ApplicationComponent;
import com.name.mviboilerplate.dagger.ApplicationModule;
import com.name.mviboilerplate.dagger.DaggerApplicationComponent;
import com.name.mviboilerplate.dagger.StoreModule;
import timber.log.Timber;

public class MviBoilerplateApplication extends Application {

  ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    initComponent();
  }

  public static MviBoilerplateApplication get(Context context) {
    return (MviBoilerplateApplication) context.getApplicationContext();
  }

  public void initComponent() {
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .apiModule(new ApiModule())
        .storeModule(new StoreModule())
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
