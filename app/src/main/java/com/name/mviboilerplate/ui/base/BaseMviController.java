package com.name.mviboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import com.name.mviboilerplate.ui.base.mvi.MviPresenter;
import com.name.mviboilerplate.ui.base.mvi.MviView;
import com.name.mviboilerplate.ui.base.mvi.MviViewState;
import javax.inject.Inject;

/**
 * Abstract Controller that every other Controller in this application must implement.
 *
 * See https://github.com/bluelinelabs/Conductor/issues/120
 * for explanation of why a custom Dagger scope is not used for state persistence here
 */
public abstract class BaseMviController<V extends MviView, VS extends MviViewState, P extends MviPresenter<V, VS>>
    extends BaseController {

  @Inject protected P presenter;

  public BaseMviController(Bundle args) {
    super(args);
  }

  @Override protected void onAttach(@NonNull View view) {
    presenter.attachView((V) this);
    super.onAttach(view);
  }

  @Override protected void onDetach(@NonNull View view) {
    super.onDetach(view);
    presenter.detachView(false); // TODO retain?
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    // TODO Icepick.saveInstanceState(this, outState);
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    // TODO Icepick.restoreInstanceState(this, savedInstanceState);
  }
}