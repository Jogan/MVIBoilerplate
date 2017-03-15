package com.name.mvpboilerplate.dagger.component;

import android.app.Application;
import android.content.Context;
import com.name.mvpboilerplate.dagger.ApplicationContext;
import com.name.mvpboilerplate.dagger.module.ApiModule;
import com.name.mvpboilerplate.dagger.module.ApplicationModule;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import com.name.mvpboilerplate.ui.base.BaseSchedulerProvider;
import dagger.Component;
import javax.inject.Singleton;
import retrofit2.Retrofit;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {

  @ApplicationContext
  Context context();

  Application application();

  MvpBoilerplateService mvpBoilerplateService();

  BaseSchedulerProvider getSchedulerProvider();

  Retrofit retrofit();
}
