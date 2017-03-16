package com.name.mvpboilerplate.dagger;

import com.name.mvpboilerplate.ui.main.MainPresenterTest;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        MockTestModule.class
    }
)
public interface MockTestComponent {

  void inject(MainPresenterTest mainPresenterTest);
}