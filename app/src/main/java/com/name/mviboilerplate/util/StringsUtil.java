package com.name.mviboilerplate.util;

import android.support.annotation.Nullable;

public final class StringsUtil {

    private StringsUtil() {
        throw new AssertionError("No instances.");
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable final CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(@Nullable final CharSequence str) {
        return !isEmpty(str);
    }
}

