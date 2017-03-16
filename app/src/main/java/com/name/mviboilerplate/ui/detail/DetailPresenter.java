package com.name.mviboilerplate.ui.detail;

import android.support.annotation.NonNull;
import com.name.mviboilerplate.data.DataManager;
import com.name.mviboilerplate.ui.base.BaseSchedulerProvider;
import com.name.mviboilerplate.ui.base.mvi.MviBasePresenter;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;

public class DetailPresenter extends MviBasePresenter<DetailView, DetailViewState> {

  private final DataManager dataManager;
  private CompositeDisposable subscriptions;

  @Inject
  public DetailPresenter(BaseSchedulerProvider schedulerProvider, DataManager dataManager) {
    super(schedulerProvider);
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(@NonNull DetailView mvpView) {
    subscriptions = new CompositeDisposable();
    super.attachView(mvpView);
  }

  @Override
  public void detachView(boolean retain) {
    super.detachView(retain);
    subscriptions.dispose();
    subscriptions = null;
  }

  @Override protected void bindIntents() {

    Observable<DetailViewState> loadData = getView()
        .loadDataIntent()
        .doOnNext(name -> Timber.d("intent: load data for %s", name))
        .switchMap(dataManager::getPokemon)
        .map(pokemon -> DetailViewState.builder().data(pokemon).build())
        .startWith(DetailViewState.builder().loading(true).build())
        .onErrorReturn(t -> DetailViewState.builder().error(t).build())
        .observeOn(provider.ui())
        .subscribeOn(provider.io());

    subscriptions.add(
        loadData.doOnNext(viewState -> Timber.d("## DetailViewState -> %s", viewState.toString()))
                .subscribe(pokemon -> getView().render(pokemon))
    );
  }
}
