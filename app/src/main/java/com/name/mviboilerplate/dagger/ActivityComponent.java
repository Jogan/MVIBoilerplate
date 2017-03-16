package com.name.mviboilerplate.dagger;

import com.name.mviboilerplate.ui.base.BaseActivity;
import com.name.mviboilerplate.ui.MainActivity;
import dagger.Component;

@Component(
    dependencies = { ApplicationComponent.class },
    modules = ActivityModule.class)
@ActivityScope
public interface ActivityComponent {
  void inject(BaseActivity baseActivity);

  void inject(MainActivity mainActivity);
}
