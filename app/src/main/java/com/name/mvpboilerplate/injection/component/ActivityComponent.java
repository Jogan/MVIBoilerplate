package com.name.mvpboilerplate.injection.component;

import com.name.mvpboilerplate.injection.PerActivity;
import com.name.mvpboilerplate.injection.module.ActivityModule;
import com.name.mvpboilerplate.ui.base.BaseActivity;
import com.name.mvpboilerplate.ui.detail.DetailActivity;
import com.name.mvpboilerplate.ui.main.MainActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);
    void inject(MainActivity mainActivity);
    void inject(DetailActivity detailActivity);
}
