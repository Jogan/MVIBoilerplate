package com.name.mviboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

/**
 * Abstract activity that handles the creation of Conductor components
 */
public abstract class ConductorActivity extends ButterKnifeActivity {

  protected Router router;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    router = Conductor.attachRouter(this, getControllerContainer(), savedInstanceState);
    if (!router.hasRootController()) {
      router.setRoot(RouterTransaction.with(getRootController()));
    }
  }

  @Override
  public void onBackPressed() {
    if (!router.handleBack()) {
      super.onBackPressed();
    }
  }

  protected abstract Controller getRootController();

  protected abstract ViewGroup getControllerContainer();
}