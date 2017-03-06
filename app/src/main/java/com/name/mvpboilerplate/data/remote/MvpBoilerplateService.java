package com.name.mvpboilerplate.data.remote;


import com.name.mvpboilerplate.data.model.NamedResource;
import com.name.mvpboilerplate.data.model.Pokemon;

import io.reactivex.Single;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MvpBoilerplateService {

    @GET("pokemon")
    Single<PokemonListResponse> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{name}")
    Single<Pokemon> getPokemon(@Path("name") String name);

    class PokemonListResponse {
        public List<NamedResource> results;
    }

}
