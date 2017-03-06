package com.name.mvpboilerplate.ui.main;

import com.name.mvpboilerplate.ui.base.MvpView;

import java.util.List;

public interface MainMvpView extends MvpView {

    void showPokemon(List<String> pokemon);

    void showProgress(boolean show);

    void showError();

}