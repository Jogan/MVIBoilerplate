package com.name.mviboilerplate.ui.base.mvi;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import com.name.mviboilerplate.ui.base.BaseSchedulerProvider;

public abstract class MviBasePresenter<V extends MviView, VS extends MviViewState>
    extends MviNullObjectBasePresenter<V, VS> {

  protected BaseSchedulerProvider provider;

  public MviBasePresenter(BaseSchedulerProvider provider) {
    this.provider = provider;
  }

  @Override public void attachView(@NonNull V view) {
    super.attachView(view);
    if (viewAttachedAtLeastOnce) {
      bindIntents();
    }
  }

  @Override public void detachView(boolean retainInstance) {
    super.detachView(retainInstance);
    unbindIntents();
  }

  /**
   * This method is called one the view is attached for the very first time to this presenter.
   * It will not called again for instance during screen orientation changes when the view will be
   * detached temporarily.
   *
   * <p>
   * The counter part of this method is  {@link #unbindIntents()}.
   * This {@link #bindIntents()} and {@link #unbindIntents()} are kind of representing the
   * lifecycle of this Presenter.
   * {@link #bindIntents()} is called the first time the view is attached
   * and {@link #unbindIntents()} is called once the view is detached permanently because it has
   * been destroyed and hence this presenter is not needed anymore and will also be destroyed
   * afterwards
   * </p>
   */
  @MainThread abstract protected void bindIntents();

  /**
   * This method will be called once the view has been detached permanently and hence the presenter
   * will be "destroyed" too. This is the correct time for doing some cleanup like unsubscribe from
   * some RxSubscriptions etc.
   *
   * * <p>
   * The counter part of this method is  {@link #bindIntents()} ()}.
   * This {@link #bindIntents()} and {@link #unbindIntents()} are kind of representing the
   * lifecycle of this Presenter.
   * {@link #bindIntents()} is called the first time the view is attached
   * and {@link #unbindIntents()} is called once the view is detached permanently because it has
   * been destroyed and hence this presenter is not needed anymore and will also be destroyed
   * afterwards
   * </p>
   */
  protected void unbindIntents() {
  }
}
