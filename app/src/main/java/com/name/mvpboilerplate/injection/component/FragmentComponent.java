package com.name.mvpboilerplate.injection.component;

import com.name.mvpboilerplate.injection.PerFragment;
import com.name.mvpboilerplate.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}