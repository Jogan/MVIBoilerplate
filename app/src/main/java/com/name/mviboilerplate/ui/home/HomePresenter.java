package com.name.mviboilerplate.ui.home;

import android.support.annotation.NonNull;
import com.name.mviboilerplate.data.DataManager;
import com.name.mviboilerplate.ui.base.BaseSchedulerProvider;
import com.name.mviboilerplate.ui.base.mvi.MviBasePresenter;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;

public class HomePresenter extends MviBasePresenter<HomeView, HomeViewState> {
  private static final int POKEMON_COUNT = 20;

  private final DataManager dataManager;
  private CompositeDisposable subscriptions;

  @Inject
  public HomePresenter(BaseSchedulerProvider schedulerProvider, DataManager dataManager) {
    super(schedulerProvider);
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(@NonNull HomeView mvpView) {
    subscriptions = new CompositeDisposable();
    super.attachView(mvpView);
  }

  @Override protected void bindIntents() {

    Observable<HomeViewPartialStateChanges> loadFirstPage = getView()
        .loadFirstPageIntent()
        .doOnNext(ignored -> Timber.d("intent: load initial data"))
        .switchMap(ignored -> dataManager
            .getPokemonList(POKEMON_COUNT)
            .map(list -> (HomeViewPartialStateChanges) new HomeViewPartialStateChanges.FirstPageLoaded(list))
            .startWith(new HomeViewPartialStateChanges.FirstPageLoading())
            .onErrorReturn(HomeViewPartialStateChanges.FirstPageError::new)
            .subscribeOn(provider.io()));

    Observable<HomeViewPartialStateChanges> pullToRefresh = getView()
        .pullToRefreshIntent()
        .doOnNext(ignored -> Timber.d("intent: pull to refresh"))
        .switchMap(ignored -> dataManager
            .getPokemonList(POKEMON_COUNT)
            .map(list -> (HomeViewPartialStateChanges) new HomeViewPartialStateChanges.PullToRefreshLoaded(list))
            .startWith(new HomeViewPartialStateChanges.PullToRefreshLoading())
            .onErrorReturn(HomeViewPartialStateChanges.PullToRefeshLoadingError::new)
            .subscribeOn(provider.io()));

    Observable<HomeViewPartialStateChanges> allIntentsObservable = Observable
        .merge(loadFirstPage, pullToRefresh)
        .observeOn(provider.ui());

    HomeViewState initialState = HomeViewState
        .builder()
        .loadingFirstPage(true)
        .build();

    subscriptions.add(
        allIntentsObservable
            .scan(initialState, this::viewStateReducer)
            .distinctUntilChanged()
            .doOnNext(viewState -> Timber.d("## HomeViewState -> %s", viewState.toString()))
            .subscribe(mainViewState -> getView().render(mainViewState))
    );
  }

  private HomeViewState viewStateReducer(HomeViewState previousState, HomeViewPartialStateChanges partialChanges) {
    return partialChanges.computeNewState(previousState);
  }

  @Override
  public void detachView(boolean retain) {
    super.detachView(retain);
    subscriptions.dispose();
    subscriptions = null;
  }
}
