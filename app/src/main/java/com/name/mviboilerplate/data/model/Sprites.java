package com.name.mviboilerplate.data.model;

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

    public static Builder builder() {
        return new AutoValue_Sprites.Builder();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder frontDefault(String frontDefault);

        public abstract Sprites build();
    }
}
