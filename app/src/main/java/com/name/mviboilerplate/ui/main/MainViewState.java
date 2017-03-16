package com.name.mviboilerplate.ui.main;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.name.mviboilerplate.ui.base.mvi.MviViewState;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class MainViewState implements MviViewState, Parcelable {

  public abstract boolean loadingFirstPage();

  @Nullable
  public abstract Throwable firstPageError();

  public abstract List<String> data();

  public abstract boolean loadingPullToRefresh();

  @Nullable
  public abstract Throwable pullToRefreshError();

  public static Builder builder() {
    return new AutoValue_MainViewState
        .Builder()
        .loadingFirstPage(false)
        .firstPageError(null)
        .data(Collections.emptyList())
        .loadingPullToRefresh(false)
        .pullToRefreshError(null);
  }

  public Builder toBuilder() {
    return new AutoValue_MainViewState.Builder(this);
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder loadingFirstPage(boolean loadingFirstPage);

    public abstract Builder firstPageError(Throwable firstPageError);

    public abstract Builder data(List<String> data);

    public abstract Builder loadingPullToRefresh(boolean loadingPullToRefresh);

    public abstract Builder pullToRefreshError(Throwable pullToRefreshError);

    public abstract MainViewState build();
  }
}
