package com.name.mvpboilerplate.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class ViewUtil {

    private ViewUtil() {
        throw new AssertionError("No instances.");
    }

    public static float pxToDp(final float px) {
        final float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(final int dp) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void hideKeyboard(final Activity activity) {
        final InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void setVisible(final View view, final boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

}
