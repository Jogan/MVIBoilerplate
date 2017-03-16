package com.name.mviboilerplate.dagger;

import android.app.Application;
import com.name.mviboilerplate.data.remote.MvpBoilerplateService;
import com.name.mviboilerplate.ui.base.BaseSchedulerProvider;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class })
public interface ApplicationComponent {

  Application application();

  MvpBoilerplateService mvpBoilerplateService();

  BaseSchedulerProvider getSchedulerProvider();
}
