package com.name.mviboilerplate.dagger;

import android.app.Application;
import com.google.gson.Gson;
import com.name.mviboilerplate.data.remote.MvpBoilerplateService;
import com.name.mviboilerplate.data.remote.Responses.PokemonListResponse;
import com.name.mviboilerplate.data.store.DefaultStoreHelper;
import com.name.mviboilerplate.data.store.StoreHelper;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.MemoryPolicy;
import com.nytimes.android.external.store.base.impl.Store;
import com.nytimes.android.external.store.base.impl.StoreBuilder;
import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.BackpressureStrategy;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

@Module
public class StoreModule {

  private static final int POKEMON_COUNT = 20;

  public StoreModule() {
  }

  @Provides @Singleton File provideFile(final Application app) {
    return app.getCacheDir();
  }

  @Provides @Singleton StoreHelper provideStoreHelper(final Gson gson, final File file) {
    return new DefaultStoreHelper(gson, file);
  }

  // Persisted
  /*@Provides @Singleton
  public Store<PokemonListResponse, BarCode> providePersistedPokemonResponse(
      StoreHelper storeHelper, MvpBoilerplateService api) {
    return StoreBuilder.<BarCode, BufferedSource, PokemonListResponse>parsedWithKey()
        .fetcher(barCode -> RxJavaInterop
            .toV1Observable(
                api.fetchPokemonForPersister(POKEMON_COUNT).map(ResponseBody::source), BackpressureStrategy.MISSING))
        .persister(storeHelper.getDefaultPersister())
        .parser(storeHelper.getDefaultParser(PokemonListResponse.class))
        .open();
  }*/

  @Provides @Singleton
  public Store<PokemonListResponse, BarCode> provideNonPersistedPokemonResponse(MvpBoilerplateService api) {
    return StoreBuilder.<PokemonListResponse>barcode()
        .fetcher(barCode -> RxJavaInterop
            .toV1Observable(api.getPokemonList(POKEMON_COUNT), BackpressureStrategy.MISSING))
        .memoryPolicy(MemoryPolicy.builder()
                                  .setExpireAfter(10)
                                  .setExpireAfterTimeUnit(TimeUnit.SECONDS)
                                  .build())
        .open();
  }
}
