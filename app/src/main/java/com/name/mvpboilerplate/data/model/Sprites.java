package com.name.mvpboilerplate.data.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Sprites implements Parcelable {
    @SerializedName("front_default")
    public abstract String frontDefault();

    public static TypeAdapter<Sprites> typeAdapter(Gson gson) {
        return new AutoValue_Sprites.GsonTypeAdapter(gson);
    }
}
