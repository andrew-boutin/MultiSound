package com.andrewboutin.multisound;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Andrew on 8/15/2015.
 */
public final class Utility {
    private static Utility utility;
    private static Context context;

    private Utility(){}

    public static void setUp(Context context){
        Utility.context = context.getApplicationContext();
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow((null == view) ? null : view.getWindowToken(),
                                                   InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
