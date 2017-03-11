package com.name.mvpboilerplate.ui.detail;

import com.name.mvpboilerplate.dagger.ConfigPersistent;
import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.ui.base.mvi.MviBasePresenter;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

@ConfigPersistent
public class DetailPresenter extends MviBasePresenter<DetailMvpView, DetailViewState> {

  private final DataManager dataManager;
  private CompositeDisposable subscriptions;

  @Inject
  public DetailPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(DetailMvpView mvpView) {
    super.attachView(mvpView);
    subscriptions = new CompositeDisposable();
  }

  @Override
  public void detachView(boolean retain) {
    super.detachView(retain);
    subscriptions.dispose();
    subscriptions = null;
  }

  @Override protected void bindIntents() {

  }

 /* public void getPokemon(String name) {
    checkViewAttached();
    getMvpView().showProgress(true);
    subscriptions.add(dataManager
        .getPokemon(name)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<Pokemon>() {
          @Override
          public void onSuccess(Pokemon pokemon) {
            getMvpView().showProgress(false);
            getMvpView().showPokemon(pokemon);
            for (Statistic statistic : pokemon.stats) {
              getMvpView().showStat(statistic);
            }
          }

          @Override
          public void onError(Throwable error) {
            getMvpView().showProgress(false);
            getMvpView().showError();
            Timber
                .e(error, "There was a problem retrieving the pokemon...");
          }
        }));
  }*/
}
