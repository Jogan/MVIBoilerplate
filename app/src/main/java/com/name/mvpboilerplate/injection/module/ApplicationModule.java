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
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MvpBoilerplateService provideMvpBoilerplateService() {
        return MvpBoilerplateServiceFactory.makeSecretsService();
    }
}
