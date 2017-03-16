package com.name.mviboilerplate.dagger;

import com.name.mviboilerplate.ui.base.BaseActivity;
import com.name.mviboilerplate.ui.detail.DetailActivity;
import com.name.mviboilerplate.ui.main.MainActivity;
import dagger.Component;

@Component(
    dependencies = { ApplicationComponent.class },
    modules = ActivityModule.class)
@ActivityScope
public interface ActivityComponent {
  void inject(BaseActivity baseActivity);

  void inject(MainActivity mainActivity);

  void inject(DetailActivity detailActivity);
}
