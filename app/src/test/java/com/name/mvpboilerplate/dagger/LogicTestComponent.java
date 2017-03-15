package com.name.mvpboilerplate.dagger;

import com.name.mvpboilerplate.data.DataManagerTest;
import com.name.mvpboilerplate.ui.main.MainPresenterTest;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        LogicTestModule.class
    }
)
public interface LogicTestComponent {

  void inject(MainPresenterTest mainPresenterTest);

  void inject(DataManagerTest dataManagerTest);
}