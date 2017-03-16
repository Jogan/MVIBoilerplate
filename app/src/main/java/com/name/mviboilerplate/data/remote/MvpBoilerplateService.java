package com.name.mviboilerplate.data.remote;


import com.name.mviboilerplate.data.model.NamedResource;
import com.name.mviboilerplate.data.model.Pokemon;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MvpBoilerplateService {

    @GET("pokemon")
    Observable<PokemonListResponse> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{name}")
    Observable<Pokemon> getPokemon(@Path("name") String name);

    class PokemonListResponse {
        public List<NamedResource> results;
    }

}
