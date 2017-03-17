package com.name.mviboilerplate.data.remote;


import com.name.mviboilerplate.data.model.Pokemon;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MvpBoilerplateService {

    @GET("pokemon")
    Observable<ResponseBody> fetchPokemonForPersister(@Query("limit") int limit);

    @GET("pokemon")
    Observable<Responses.PokemonListResponse> getPokemonList(@Query("limit") int limit);

    @GET("pokemon/{name}")
    Observable<Pokemon> getPokemon(@Path("name") String name);

}
