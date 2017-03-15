package com.name.mvpboilerplate.data;

import com.name.mvpboilerplate.data.model.NamedResource;
import com.name.mvpboilerplate.data.model.Pokemon;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import com.name.mvpboilerplate.ui.main.MainViewState;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

public class DataManager {

  private final MvpBoilerplateService mvpBoilerplateService;

  @Inject
  public DataManager(MvpBoilerplateService mvpBoilerplateService) {
    this.mvpBoilerplateService = mvpBoilerplateService;
  }

  // TODO replace with repositories and NYT Stores
  public Observable<List<String>> getPokemonList(int limit) {
    return mvpBoilerplateService
        .getPokemonList(limit)
        .flatMap(pokemonListResponse -> {
          List<String> pokemonNames = new ArrayList<>();
          for (NamedResource pokemon : pokemonListResponse.results) {
            pokemonNames.add(pokemon.name());
          }
          return Observable.just(pokemonNames);
        });
  }

  public Observable<Pokemon> getPokemon(String name) {
    return mvpBoilerplateService.getPokemon(name);
  }
}