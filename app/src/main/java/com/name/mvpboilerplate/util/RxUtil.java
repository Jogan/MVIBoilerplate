package com.name.mvpboilerplate.util;

import io.reactivex.disposables.Disposable;

public final class RxUtil {

    private RxUtil() {
        throw new AssertionError("No instances.");
    }

    /**
     * Returns a boolean indicating whether a subscription is already being made
     */
    public static boolean inFlight(final Disposable subscription) {
        return subscription != null && !subscription.isDisposed();
    }

    /**
     * UnSubscribe if the subscription is in flight
     */
    public static void unSubscribeIfNeeded(final Disposable subscription) {
        if (inFlight(subscription)) {
            subscription.dispose();
        }
    }

    /**
     * UnSubscribe if the subscriptions are in flight
     */
    public static void unSubscribeIfNeeded(final Disposable... subscriptions) {
        for (final Disposable subscription : subscriptions) {
            unSubscribeIfNeeded(subscription);
        }
    }
}
