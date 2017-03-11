package com.name.mvpboilerplate.ui.main;

import android.support.annotation.NonNull;
import com.name.mvpboilerplate.dagger.ConfigPersistent;
import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.ui.base.mvi.MviBasePresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends MviBasePresenter<MainView, MainViewState> {
  private static final int POKEMON_COUNT = 20;

  private final DataManager dataManager;
  private CompositeDisposable subscriptions;

  @Inject
  public MainPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(@NonNull MainView mvpView) {
    subscriptions = new CompositeDisposable();
    super.attachView(mvpView);
  }

  @Override protected void bindIntents() {
    subscriptions.add(
        getView().loadFirstPageIntent()
                 .switchMap(ignored -> dataManager.getPokemonList(POKEMON_COUNT))
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(Schedulers.io())
                 .doOnNext(viewState -> Timber.d("## MainViewState -> %s", viewState.toString()))
                 .subscribe(viewState -> getView().render(viewState)));
  }

  @Override
  public void detachView(boolean retain) {
    super.detachView(retain);
    subscriptions.dispose();
    subscriptions = null;
  }
}
