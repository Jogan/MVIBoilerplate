package com.name.mviboilerplate.data.store;

import com.google.gson.Gson;
import com.nytimes.android.external.fs.SourcePersisterFactory;
import com.nytimes.android.external.store.base.Parser;
import com.nytimes.android.external.store.base.Persister;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.middleware.GsonParserFactory;
import java.io.File;
import java.io.IOException;
import javax.inject.Inject;
import okio.BufferedSource;
import timber.log.Timber;

public class DefaultStoreHelper implements StoreHelper {
  private final Gson gson;
  private final File file;

  @Inject
  public DefaultStoreHelper(final Gson gson, final File file) {
    this.gson = gson;
    this.file = file;
  }

  @Override
  public <T> Parser<BufferedSource, T> getDefaultParser(Class<T> classType) {
    return GsonParserFactory.createSourceParser(gson, classType);
  }

  @Override
  public Persister<BufferedSource, BarCode> getDefaultPersister() {
    try {
      return SourcePersisterFactory.create(file);
    } catch (IOException ex) {
      Timber.tag("Default Persister").e(ex.getMessage());
      return null;
    }
  }
}