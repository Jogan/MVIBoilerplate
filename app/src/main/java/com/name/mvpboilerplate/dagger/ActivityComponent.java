package com.name.mvpboilerplate.dagger;

import com.name.mvpboilerplate.ui.base.BaseActivity;
import com.name.mvpboilerplate.ui.detail.DetailActivity;
import com.name.mvpboilerplate.ui.main.MainActivity;
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
