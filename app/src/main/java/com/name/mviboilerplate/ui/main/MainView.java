package com.name.mviboilerplate.ui.main;

import com.name.mviboilerplate.ui.base.mvi.MviView;
import io.reactivex.Observable;

public interface MainView extends MviView {

  /**
   * The intent to load the first page
   *
   * @return The emitted item boolean can be ignored because it is always true
   */
  Observable<Boolean> loadFirstPageIntent();

  /**
   * The intent to react on pull-to-refresh
   *
   * @return The emitted item boolean can be ignored because it is always true
   */
  Observable<Boolean> pullToRefreshIntent();

  /**
   * Renders the viewState
   */
  void render(MainViewState viewState);
}