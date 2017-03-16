package com.name.mviboilerplate.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.name.mviboilerplate.MviBoilerplateApplication;
import com.name.mviboilerplate.dagger.ActivityComponent;
import com.name.mviboilerplate.dagger.ActivityModule;
import com.name.mviboilerplate.dagger.ApplicationComponent;
import com.name.mviboilerplate.dagger.DaggerActivityComponent;

public abstract class BaseActivity extends ConductorActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ApplicationComponent appComponent = ((MviBoilerplateApplication) getApplication()).getApplicationComponent();
    ActivityComponent activityComponent = DaggerActivityComponent
        .builder()
        .applicationComponent(appComponent)
        .activityModule(new ActivityModule(this))
        .build();
    injectFrom(activityComponent);
  }

  protected abstract void injectFrom(ActivityComponent activityComponent);
}
