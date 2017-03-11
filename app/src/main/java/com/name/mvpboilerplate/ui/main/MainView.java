package com.name.mvpboilerplate.ui.main;

import com.name.mvpboilerplate.ui.base.mvi.MviView;
import io.reactivex.Observable;

public interface MainView extends MviView {

  /**
   * The intent to load the first page
   *
   * @return The emitted item boolean can be ignored because it is always true
   */
  Observable<Boolean> loadFirstPageIntent();

  /**
   * Renders the viewState
   */
  void render(MainViewState viewState);
}