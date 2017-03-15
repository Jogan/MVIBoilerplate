package com.name.mvpboilerplate.ui.detail;

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

    subscriptions.add(loadData.subscribe(pokemon -> getView().render(pokemon)));
  }
}
