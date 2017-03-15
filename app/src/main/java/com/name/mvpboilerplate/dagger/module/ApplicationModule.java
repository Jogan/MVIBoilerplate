package com.name.mvpboilerplate.dagger.module;

import android.app.Application;
import android.content.Context;
import com.name.mvpboilerplate.dagger.ApplicationContext;
import com.name.mvpboilerplate.data.SchedulerProvider;
import com.name.mvpboilerplate.ui.base.BaseSchedulerProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {
  protected final Application application;

  public ApplicationModule(Application application) {
    this.application = application;
  }

  @Provides
  Application provideApplication() {
    return application;
  }

  @Provides
  @ApplicationContext
  Context provideContext() {
    return application;
  }

  @Provides
  @Singleton
  BaseSchedulerProvider providerSchedulerProvider(SchedulerProvider provider) {
    return provider;
  }
}
