package com.name.mviboilerplate.dagger;

import com.name.mviboilerplate.data.DataManagerTest;
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