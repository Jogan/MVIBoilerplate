package com.name.mviboilerplate.dagger;

import android.app.Application;
import com.name.mviboilerplate.data.remote.MvpBoilerplateService;
import com.name.mviboilerplate.data.remote.Responses;
import com.name.mviboilerplate.ui.base.BaseSchedulerProvider;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.Store;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { ApplicationModule.class, ApiModule.class, StoreModule.class })
public interface ApplicationComponent {

  Application application();

  MvpBoilerplateService mvpBoilerplateService();

  BaseSchedulerProvider getSchedulerProvider();

  Store<Responses.PokemonListResponse, BarCode> pokemonListStore();
}
