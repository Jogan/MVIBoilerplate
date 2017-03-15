package com.name.mvpboilerplate.data;

import com.name.mvpboilerplate.ui.base.BaseSchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class SchedulerProvider implements BaseSchedulerProvider {

  @Inject
  public SchedulerProvider() {
  }

  @Override
  @NonNull
  public Scheduler computation() {
    return Schedulers.computation();
  }

  @Override
  @NonNull
  public Scheduler io() {
    return Schedulers.io();
  }

  @Override
  @NonNull
  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}