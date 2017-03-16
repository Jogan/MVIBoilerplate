package com.name.mviboilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Abstract activity that handles ButterKnife setup
 */
public abstract class ButterKnifeActivity extends AppCompatActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    ButterKnife.bind(this);
  }

  protected abstract int getLayout();
}