package com.name.mviboilerplate.dagger;

import com.name.mviboilerplate.ui.detail.DetailController;
import com.name.mviboilerplate.ui.home.HomeController;
import dagger.Component;

@Component(
    dependencies = { ApplicationComponent.class },
    modules = ControllerModule.class)
@ControllerScope
public interface ControllerComponent {

  void inject(HomeController homeController);

  void inject(DetailController detailController);
}
