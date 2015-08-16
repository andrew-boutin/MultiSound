package com.andrewboutin.multisound;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Andrew on 8/15/2015.
 *
 * Provides small utility methods to classes thoughout the app.
 */
public final class Utility {
    private static Context context;

    private Utility(){} // Don't instantiate instances

    /**
     * Handles setting up the Utility class for use.
     *
     * @param context
     */
    public static void setUp(Context context){
        Utility.context = context.getApplicationContext();
    }

    /**
     * Handles closing the user's keyboard.
     *
     * @param view View that is in use as the keyboard is in use.
     */
    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow((null == view) ? null : view.getRootView().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}