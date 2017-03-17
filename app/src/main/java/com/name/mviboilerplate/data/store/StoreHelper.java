package com.name.mviboilerplate.data.store;

import com.nytimes.android.external.store.base.Parser;
import com.nytimes.android.external.store.base.Persister;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.Store;
import okio.BufferedSource;

/**
 * StoreHelper
 * <p>
 * Interface defining methods that will retrieve {@link Store}
 * elements like {@link Parser} and {@link Persister}.
 */
public interface StoreHelper {

  <T> Parser<BufferedSource, T> getDefaultParser(Class<T> classType);

  Persister<BufferedSource, BarCode> getDefaultPersister();
}