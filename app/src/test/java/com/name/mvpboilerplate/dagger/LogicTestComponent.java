package com.name.mvpboilerplate.dagger;

import com.name.mvpboilerplate.data.DataManagerTest;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        LogicTestModule.class
    }
)
public interface LogicTestComponent {

  void inject(DataManagerTest dataManagerTest);
}