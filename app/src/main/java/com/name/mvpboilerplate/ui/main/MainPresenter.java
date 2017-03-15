package com.name.mvpboilerplate.ui.main;

import android.support.annotation.NonNull;
import com.name.mvpboilerplate.dagger.ConfigPersistent;
import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.ui.base.BaseSchedulerProvider;
import com.name.mvpboilerplate.ui.base.mvi.MviBasePresenter;
import io.reactivex.Observable;
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
  public MainPresenter(BaseSchedulerProvider schedulerProvider, DataManager dataManager) {
    super(schedulerProvider);
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(@NonNull MainView mvpView) {
    subscriptions = new CompositeDisposable();
    super.attachView(mvpView);
  }

  @Override protected void bindIntents() {

    Observable<MainViewPartialStateChanges> loadFirstPage = getView()
        .loadFirstPageIntent()
        .doOnNext(ignored -> Timber.d("intent: load initial data"))
        .switchMap(ignored -> dataManager
            .getPokemonList(POKEMON_COUNT)
            .map(list -> (MainViewPartialStateChanges) new MainViewPartialStateChanges.FirstPageLoaded(list))
            .startWith(new MainViewPartialStateChanges.FirstPageLoading())
            .onErrorReturn(MainViewPartialStateChanges.FirstPageError::new)
            .subscribeOn(provider.io()));

    Observable<MainViewPartialStateChanges> pullToRefresh = getView()
        .pullToRefreshIntent()
        .doOnNext(ignored -> Timber.d("intent: pull to refresh"))
        .switchMap(ignored -> dataManager
            .getPokemonList(POKEMON_COUNT)
            .map(list -> (MainViewPartialStateChanges) new MainViewPartialStateChanges.PullToRefreshLoaded(list))
            .startWith(new MainViewPartialStateChanges.PullToRefreshLoading())
            .onErrorReturn(MainViewPartialStateChanges.PullToRefeshLoadingError::new)
            .subscribeOn(provider.io()));

    Observable<MainViewPartialStateChanges> allIntentsObservable = Observable
        .merge(loadFirstPage, pullToRefresh)
        .observeOn(provider.ui());

    MainViewState initialState = MainViewState
        .builder()
        .loadingFirstPage(true)
        .build();

    subscriptions.add(
        allIntentsObservable
            .scan(initialState, this::viewStateReducer)
            .distinctUntilChanged()
            .doOnNext(viewState -> Timber.d("## MainViewState -> %s", viewState.toString()))
            .subscribe(mainViewState -> getView().render(mainViewState))
    );
  }

  private MainViewState viewStateReducer(MainViewState previousState, MainViewPartialStateChanges partialChanges) {
    return partialChanges.computeNewState(previousState);
  }

  @Override
  public void detachView(boolean retain) {
    super.detachView(retain);
    subscriptions.dispose();
    subscriptions = null;
  }
}
