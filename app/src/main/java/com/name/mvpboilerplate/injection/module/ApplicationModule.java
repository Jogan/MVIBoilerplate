package com.name.mvpboilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateServiceFactory;
import com.name.mvpboilerplate.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    MvpBoilerplateService provideMvpBoilerplateService() {
        return MvpBoilerplateServiceFactory.makeSecretsService();
    }
}
