package com.name.mviboilerplate.data;

import com.name.mviboilerplate.data.model.Pokemon;
import com.name.mviboilerplate.data.remote.MvpBoilerplateService;
import io.reactivex.Observable;
import javax.inject.Inject;

public class DataManager {

  private final MvpBoilerplateService mvpBoilerplateService;

  @Inject
  public DataManager(MvpBoilerplateService mvpBoilerplateService) {
    this.mvpBoilerplateService = mvpBoilerplateService;
  }

  public Observable<Pokemon> getPokemon(String name) {
    return mvpBoilerplateService.getPokemon(name);
  }
}