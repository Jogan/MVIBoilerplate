package com.name.mvpboilerplate.dagger.component;

import com.name.mvpboilerplate.dagger.PerActivity;
import com.name.mvpboilerplate.dagger.module.ActivityModule;
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
