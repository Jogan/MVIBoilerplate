package com.name.mviboilerplate.ui.detail;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.name.mviboilerplate.data.model.Pokemon;
import com.name.mviboilerplate.ui.base.mvi.MviViewState;

@AutoValue
public abstract class DetailViewState implements MviViewState, Parcelable {

  public abstract boolean loading();

  @Nullable
  public abstract Throwable error();

  @Nullable
  public abstract Pokemon data();

  public static Builder builder() {
    return new AutoValue_DetailViewState
        .Builder()
        .loading(false)
        .error(null)
        .data(null);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder loading(boolean loading);

    public abstract Builder error(Throwable error);

    public abstract Builder data(Pokemon data);

    public abstract DetailViewState build();
  }
}
