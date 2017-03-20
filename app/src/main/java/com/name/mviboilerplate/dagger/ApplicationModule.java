package com.name.mviboilerplate.dagger;

import android.app.Application;
import com.name.mviboilerplate.data.SchedulerProvider;
import com.name.mviboilerplate.data.BaseSchedulerProvider;
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
