package com.name.mviboilerplate.dagger;

import com.bluelinelabs.conductor.Controller;
import dagger.Module;
import dagger.Provides;

@Module
@ControllerScope
public class ControllerModule {

    private Controller controller;

    public ControllerModule(Controller controller) {
        this.controller = controller;
    }

    @Provides
    Controller provideController() {
        return controller;
    }
}
