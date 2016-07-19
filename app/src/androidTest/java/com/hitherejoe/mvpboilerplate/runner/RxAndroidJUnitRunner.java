package com.name.mvpboilerplate.runner;

import android.os.Bundle;
import android.support.test.espresso.Espresso;

import com.name.mvpboilerplate.util.RxIdlingExecutionHook;
import com.name.mvpboilerplate.util.RxIdlingResource;

import rx.plugins.RxJavaPlugins;

/**
 * Runner that registers a Espresso Indling resource that handles waiting for
 * RxJava Observables to finish.
 * WARNING - Using this runner will block the tests if the application uses long-lived hot
 * Observables such us event buses, etc.
 */
public class RxAndroidJUnitRunner extends UnlockDeviceAndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        final RxIdlingResource rxIdlingResource = new RxIdlingResource();
        RxJavaPlugins.getInstance()
                .registerObservableExecutionHook(new RxIdlingExecutionHook(rxIdlingResource));
        Espresso.registerIdlingResources(rxIdlingResource);
    }
}