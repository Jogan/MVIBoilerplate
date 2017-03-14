package com.name.mvpboilerplate.data.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Statistic implements Parcelable {
    public abstract NamedResource stat();
    @SerializedName("base_stat")
    public abstract int baseStat();

    public static TypeAdapter<Statistic> typeAdapter(Gson gson) {
        return new AutoValue_Statistic.GsonTypeAdapter(gson);
    }
}
