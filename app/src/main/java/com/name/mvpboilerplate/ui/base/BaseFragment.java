package com.name.mvpboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.name.mvpboilerplate.MvpBoilerplateApplication;
import com.name.mvpboilerplate.injection.component.ConfigPersistentComponent;
import com.name.mvpboilerplate.injection.component.DaggerConfigPersistentComponent;
import com.name.mvpboilerplate.injection.component.FragmentComponent;
import com.name.mvpboilerplate.injection.module.FragmentModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

/**
 * Abstract Fragment that every other Fragment in this application must implement. It handles
 * creation of Dagger components and makes sure that instances of ConfigPersistentComponent are kept
 * across configuration changes.
 */
public abstract class BaseFragment extends Fragment {

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final Map<Long, ConfigPersistentComponent> componentsMap = new HashMap<>();
    private static final AtomicLong NEXT_ID = new AtomicLong(0);

    private FragmentComponent fragmentComponent;
    private long fragmentId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the FragmentComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        fragmentId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_FRAGMENT_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!componentsMap.containsKey(fragmentId)) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", fragmentId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(MvpBoilerplateApplication.get(
                            getActivity()).getComponent())
                    .build();
            componentsMap.put(fragmentId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", fragmentId);
            configPersistentComponent = componentsMap.get(fragmentId);
        }
        fragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_FRAGMENT_ID, fragmentId);
    }

    @Override
    public void onDestroy() {
        if (!getActivity().isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", fragmentId);
            componentsMap.remove(fragmentId);
        }
        super.onDestroy();
    }

    public FragmentComponent fragmentComponent() {
        return fragmentComponent;
    }
}
