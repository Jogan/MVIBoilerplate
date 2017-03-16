package com.name.mviboilerplate.data;

import com.name.mviboilerplate.data.model.NamedResource;
import com.name.mviboilerplate.data.model.Pokemon;
import com.name.mviboilerplate.data.remote.MvpBoilerplateService;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

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