package com.name.mviboilerplate.data.model;

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

    public static Builder builder() {
        return new AutoValue_NamedResource.Builder().url("");
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder name(String name);

        public abstract Builder url(String url);

        public abstract NamedResource build();
    }
}
