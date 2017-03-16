package com.name.mvpboilerplate.dagger;

import android.app.Application;
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
  @Singleton
  Application provideApplication() {
    return application;
  }

  @Provides
  @Singleton
  BaseSchedulerProvider providerSchedulerProvider(SchedulerProvider provider) {
    return provider;
  }
}
