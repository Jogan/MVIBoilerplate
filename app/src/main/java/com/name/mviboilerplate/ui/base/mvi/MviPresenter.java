package com.name.mviboilerplate.ui.base.mvi;

/**
 * This type of presenter is responsible to interact with the viewState in a Model-View-Intent way.
 * A {@link MviPresenter} is the bridge that is responsible to setup the reactive flow between
 * viewState and model
 *
 * @param <V> The type of the View this presenter responds to
 * @param <VS> The type of the MviViewState (Model)
 */
public interface MviPresenter<V extends MviView, VS extends MviViewState>  {
  void attachView(V mviView);

  void detachView(boolean retainInstance);
}