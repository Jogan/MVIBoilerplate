package com.name.mvpboilerplate.dagger.component;

import com.name.mvpboilerplate.dagger.PerFragment;
import com.name.mvpboilerplate.dagger.module.FragmentModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}