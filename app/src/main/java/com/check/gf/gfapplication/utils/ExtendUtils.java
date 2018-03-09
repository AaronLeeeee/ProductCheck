package com.check.gf.gfapplication.utils;

import android.view.View;

/**
 * Created by wqd on 2018/1/1.
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
