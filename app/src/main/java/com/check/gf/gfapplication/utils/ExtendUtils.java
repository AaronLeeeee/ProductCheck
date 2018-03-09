package com.check.gf.gfapplication.utils;

import android.view.View;

/**
 * @author nEdAy
 */
public class ExtendUtils {

    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        if (null == listener || null == views) {
            return;
        }
        for (View view : views) {
            if (view != null) {
                view.setOnClickListener(listener);
            }
        }
    }
}
