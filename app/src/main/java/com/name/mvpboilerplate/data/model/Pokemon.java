package com.name.mvpboilerplate.data.model;

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
}
