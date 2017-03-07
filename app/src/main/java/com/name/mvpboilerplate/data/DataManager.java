package com.name.mvpboilerplate.data;

import com.name.mvpboilerplate.data.model.NamedResource;
import com.name.mvpboilerplate.data.model.Pokemon;
import com.name.mvpboilerplate.data.remote.MvpBoilerplateService;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final MvpBoilerplateService mvpBoilerplateService;

    @Inject
    public DataManager(MvpBoilerplateService mvpBoilerplateService) {
        this.mvpBoilerplateService = mvpBoilerplateService;
    }

    public Single<List<String>> getPokemonList(int limit) {
        return mvpBoilerplateService.getPokemonList(limit)
                .flatMap(pokemonListResponse -> {
                    List<String> pokemonNames = new ArrayList<>();
                    for (NamedResource pokemon : pokemonListResponse.results) {
                        pokemonNames.add(pokemon.name);
                    }
                    return Single.just(pokemonNames);
                });
    }

    public Single<Pokemon> getPokemon(String name) {
        return mvpBoilerplateService.getPokemon(name);
    }

}