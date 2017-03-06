package com.name.mvpboilerplate.dagger.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.name.mvpboilerplate.dagger.ActivityContext;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment providesFragment() {
        return fragment;
    }

    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return fragment.getActivity();
    }

}