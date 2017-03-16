package com.name.mviboilerplate.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import butterknife.BindView;
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Controller;
import com.name.mviboilerplate.R;
import com.name.mviboilerplate.dagger.ActivityComponent;
import com.name.mviboilerplate.ui.base.BaseActivity;
import com.name.mviboilerplate.ui.home.HomeController;

public class MainActivity extends BaseActivity  {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.controller_container) ChangeHandlerFrameLayout controllerContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(toolbar);
  }

  @Override protected Controller getRootController() {
    return new HomeController();
  }

  @Override protected ViewGroup getControllerContainer() {
    return controllerContainer;
  }

  @Override protected int getLayout() {
    return R.layout.activity_main;
  }

  @Override protected void injectFrom(ActivityComponent activityComponent) {
    activityComponent.inject(this);
  }

}