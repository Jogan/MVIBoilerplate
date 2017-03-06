package com.name.mvpboilerplate.ui.detail;


import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.data.model.Pokemon;
import com.name.mvpboilerplate.data.model.Statistic;
import com.name.mvpboilerplate.dagger.ConfigPersistent;
import com.name.mvpboilerplate.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

@ConfigPersistent
public class DetailPresenter extends BasePresenter<DetailMvpView> {

    private final DataManager dataManager;
    private CompositeSubscription subscriptions;

    @Inject
    public DetailPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        subscriptions.unsubscribe();
        subscriptions = null;
    }

    public void getPokemon(String name) {
        checkViewAttached();
        getMvpView().showProgress(true);
        subscriptions.add(dataManager.getPokemon(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<Pokemon>() {
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
                        Timber.e(error, "There was a problem retrieving the pokemon...");
                    }
                }));
    }


}
