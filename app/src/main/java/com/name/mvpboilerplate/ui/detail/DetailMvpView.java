package com.name.mvpboilerplate.ui.detail;

import com.name.mvpboilerplate.data.model.Pokemon;
import com.name.mvpboilerplate.data.model.Statistic;
import com.name.mvpboilerplate.ui.base.MvpView;

public interface DetailMvpView extends MvpView {

    void showPokemon(Pokemon pokemon);

    void showStat(Statistic statistic);

    void showProgress(boolean show);

    void showError();

}