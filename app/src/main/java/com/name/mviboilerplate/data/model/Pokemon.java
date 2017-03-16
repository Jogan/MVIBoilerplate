package com.name.mviboilerplate.data.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.List;

@AutoValue
public abstract class Pokemon implements Parcelable {
    public abstract String id();
    public abstract String name();
    public abstract Sprites sprites();
    public abstract List<Statistic> stats();

    public static TypeAdapter<Pokemon> typeAdapter(Gson gson) {
        return new AutoValue_Pokemon.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_Pokemon.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder name(String name);

        public abstract Builder sprites(Sprites sprites);

        public abstract Builder stats(List<Statistic> stats);

        public abstract Pokemon build();
    }
}
