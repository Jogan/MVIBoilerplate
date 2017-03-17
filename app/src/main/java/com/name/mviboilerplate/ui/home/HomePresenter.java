package com.name.mviboilerplate.ui.home;

import android.support.annotation.NonNull;
import com.name.mviboilerplate.data.model.NamedResource;
import com.name.mviboilerplate.data.remote.Responses;
import com.name.mviboilerplate.ui.base.BaseSchedulerProvider;
import com.name.mviboilerplate.ui.base.mvi.MviBasePresenter;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.Store;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class HomePresenter extends MviBasePresenter<HomeView, HomeViewState> {

  private final Store<Responses.PokemonListResponse, BarCode> pokemonListStore;
  private final BarCode listRequest = new BarCode(Responses.PokemonListResponse.class.getSimpleName(), "list");

  private CompositeDisposable subscriptions;

  @Inject
  public HomePresenter(
      BaseSchedulerProvider schedulerProvider,
      Store<Responses.PokemonListResponse, BarCode> pokemonListStore) {
    super(schedulerProvider);
    this.pokemonListStore = pokemonListStore;
  }

  @Override
  public void attachView(@NonNull HomeView mvpView) {
    subscriptions = new CompositeDisposable();
    super.attachView(mvpView);
  }

  @Override protected void bindIntents() {
    Timber.d("## bindIntents()");

    Observable<HomeViewPartialStateChanges> loadFirstPage = getView()
        .loadFirstPageIntent()
        .doOnNext(ignored -> Timber.d("intent: load initial data"))
        .switchMap(ignored -> RxJavaInterop
            .toV2Observable(pokemonListStore.get(listRequest).compose(convertToListOfNames()))
            .map(list -> (HomeViewPartialStateChanges) new HomeViewPartialStateChanges.FirstPageLoaded(list))
            .startWith(new HomeViewPartialStateChanges.FirstPageLoading())
            .onErrorReturn(HomeViewPartialStateChanges.FirstPageError::new)
            .subscribeOn(provider.io()));

    Observable<HomeViewPartialStateChanges> pullToRefresh = getView()
        .pullToRefreshIntent()
        .doOnNext(ignored -> Timber.d("intent: pull to refresh"))
        .switchMap(ignored -> RxJavaInterop
            .toV2Observable(pokemonListStore.fetch(listRequest).compose(convertToListOfNames()))
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

  private rx.Observable.Transformer<Responses.PokemonListResponse, List<String>> convertToListOfNames() {
    return observable -> observable.map(pokemonListResponse -> pokemonListResponse.results)
                                   .concatMapIterable(list -> list)
                                   .map(NamedResource::name)
                                   .toList();
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
