package com.name.mvpboilerplate.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.name.mvpboilerplate.MvpBoilerplateApplication;
import com.name.mvpboilerplate.dagger.ActivityComponent;
import com.name.mvpboilerplate.dagger.ActivityModule;
import com.name.mvpboilerplate.dagger.ApplicationComponent;
import com.name.mvpboilerplate.dagger.DaggerActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ApplicationComponent appComponent = ((MvpBoilerplateApplication) getApplication()).getApplicationComponent();
    ActivityComponent activityComponent = DaggerActivityComponent
        .builder()
        .applicationComponent(appComponent)
        .activityModule(new ActivityModule(this))
        .build();
    injectFrom(activityComponent);
  }

  protected abstract void injectFrom(ActivityComponent activityComponent);

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
