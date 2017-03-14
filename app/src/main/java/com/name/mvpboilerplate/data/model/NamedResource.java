package com.name.mvpboilerplate.data.model;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class NamedResource implements Parcelable {
    public abstract String name();
    public abstract String url();

    public static TypeAdapter<NamedResource> typeAdapter(Gson gson) {
        return new AutoValue_NamedResource.GsonTypeAdapter(gson);
    }
}
