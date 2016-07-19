package com.name.mvpboilerplate.dagger.component;

import android.app.Application;
import android.content.Context;

import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import com.name.mvpboilerplate.dagger.ApplicationContext;
import com.name.mvpboilerplate.dagger.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    DataManager dataManager();
    MvpBoilerplateService mvpBoilerplateService();
}
