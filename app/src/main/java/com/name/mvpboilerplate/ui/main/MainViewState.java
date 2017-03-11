package com.name.mvpboilerplate.ui.main;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.name.mvpboilerplate.ui.base.mvi.MviViewState;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class MainViewState implements MviViewState, Parcelable {
  public abstract boolean loading();

  public abstract List<String> pokemon();

  public abstract boolean error();

  public static Builder builder() {
    return new AutoValue_MainViewState.Builder().loading(false)
                                                .error(false)
                                                .pokemon(Collections.emptyList());
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder loading(boolean loading);

    public abstract Builder pokemon(List<String> pokemon);

    public abstract Builder error(boolean error);

    public abstract MainViewState build();
  }
}
