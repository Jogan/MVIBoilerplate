package com.name.mviboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Controller;
import com.name.mviboilerplate.MviBoilerplateApplication;
import com.name.mviboilerplate.dagger.ApplicationComponent;
import com.name.mviboilerplate.dagger.ControllerComponent;
import com.name.mviboilerplate.dagger.ControllerModule;
import com.name.mviboilerplate.dagger.DaggerControllerComponent;

/**
 * Abstract Controller that every other Controller in this application must implement.
 *
 * See https://github.com/bluelinelabs/Conductor/issues/120
 * for explanation of why a custom Dagger scope is not used for state persistence here
 */
public abstract class BaseController extends ButterKnifeController {

  private ControllerComponent controllerComponent;

  protected BaseController() {
    this(null);
  }

  protected BaseController(Bundle args) {
    super(args);
  }

  @NonNull @Override
  protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
    injectController();
    return super.onCreateView(inflater, container);
  }

  @Override
  protected void onAttach(@NonNull View view) {
    setTitle();
    super.onAttach(view);
  }

  protected void setTitle() {
    Controller parentController = getParentController();
    while (parentController != null) {
      if (parentController instanceof BaseController &&
          ((BaseController) parentController).getTitle() != null) {
        return;
      }
      parentController = parentController.getParentController();
    }

    String title = getTitle();
    if (title != null && getActionBar() != null) {
      getActionBar().setTitle(title);
    }
  }

  @Nullable
  protected ActionBar getActionBar() {
    if (getActivity() instanceof AppCompatActivity) {
      final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
      return actionBar != null ? actionBar : null;
    } else {
      return null;
    }
  }

  public ControllerComponent getControllerComponent() {
    if (controllerComponent == null) {
      ApplicationComponent appComponent =
          ((MviBoilerplateApplication) getActivity().getApplication()).getApplicationComponent();
      controllerComponent = DaggerControllerComponent
          .builder()
          .applicationComponent(appComponent)
          .controllerModule(new ControllerModule(this))
          .build();
    }
    return controllerComponent;
  }

  protected String getTitle() {
    return null;
  }

  protected abstract void injectController();
}