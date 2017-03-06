package com.name.mvpboilerplate.ui.main;


import com.name.mvpboilerplate.data.DataManager;
import com.name.mvpboilerplate.dagger.ConfigPersistent;
import com.name.mvpboilerplate.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;
    private CompositeSubscription subscriptions;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        subscriptions.unsubscribe();
        subscriptions = null;
    }

    public void getPokemon(int limit) {
        checkViewAttached();
        getMvpView().showProgress(true);
        subscriptions.add(dataManager.getPokemonList(limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new SingleSubscriber<List<String>>() {
                    @Override
                    public void onSuccess(List<String> pokemon) {
                        getMvpView().showProgress(false);
                        getMvpView().showPokemon(pokemon);
                    }

                    @Override
                    public void onError(Throwable error) {
                        getMvpView().showProgress(false);
                        getMvpView().showError();
                        Timber.e(error, "There was an error retrieving the pokemon");
                    }
                }));
    }


}
