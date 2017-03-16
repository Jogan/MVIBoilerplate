package com.name.mviboilerplate.dagger;

import com.name.mviboilerplate.ui.main.MainPresenterTest;
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