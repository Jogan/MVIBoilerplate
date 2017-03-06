package com.name.mvpboilerplate.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public interface SchedulerTransformer {
    <T> Observable.Transformer<T, T> applySchedulers();

    class DefaultSchedulerTransformer implements SchedulerTransformer {
        @Override
        public <T> Observable.Transformer<T, T> applySchedulers() {
            return observable -> observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }

    class TestSchedulerTransformer implements SchedulerTransformer {
        @Override
        public <T> Observable.Transformer<T, T> applySchedulers() {
            return observable -> observable
                    .subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate());
        }
    }
}

