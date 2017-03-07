package com.name.mvpboilerplate.ui.main;

import com.name.mvpboilerplate.dagger.ConfigPersistent;
import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.ui.base.BasePresenter;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import org.reactivestreams.Subscriber;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

  private final DataManager dataManager;
  private CompositeDisposable subscriptions;

  @Inject
  public MainPresenter(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public void attachView(MainMvpView mvpView) {
    super.attachView(mvpView);
    subscriptions = new CompositeDisposable();
  }

  @Override
  public void detachView() {
    super.detachView();
    subscriptions.dispose();
    subscriptions = null;
  }

  public void getPokemon(int limit) {
    checkViewAttached();
    getMvpView().showProgress(true);
    subscriptions.add(dataManager
        .getPokemonList(limit)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<List<String>>() {
          @Override public void onSuccess(List<String> pokemon) {
            getMvpView().showProgress(false);
            getMvpView().showPokemon(pokemon);
          }

          @Override public void onError(Throwable e) {
            getMvpView().showProgress(false);
            getMvpView().showError();
            Timber.e(e, "There was an error retrieving the pokemon");
          }
        }));
  }
}
