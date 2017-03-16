package com.name.mvpboilerplate.dagger;

import android.app.Application;
import com.name.mvpboilerplate.dagger.ApiModule;
import com.name.mvpboilerplate.dagger.ApplicationModule;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import com.name.mvpboilerplate.ui.base.BaseSchedulerProvider;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {

  Application application();

  MvpBoilerplateService mvpBoilerplateService();

  BaseSchedulerProvider getSchedulerProvider();
}
