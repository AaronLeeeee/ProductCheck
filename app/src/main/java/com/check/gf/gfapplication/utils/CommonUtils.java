package com.check.gf.gfapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import com.check.gf.gfapplication.CustomApplication;


/**
 * 常用工具
 *
 * @author nEdAy
 */
public final class CommonUtils {
    private static Toast mToast;//静态变量 防止显示覆盖

    /**
     * 判断网络是否连接
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isNetworkAvailable() {
        NetworkInfo info = getNetworkInfo(CustomApplication.getInstance());
        return info != null && info.isAvailable();
    }

    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null ? cm.getActiveNetworkInfo() : null;
    }

    /**
     * 显示Toast
     *
     * @param text 文字
     */
    public static void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(CustomApplication.getInstance(), text, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    /**
     * 显示Toast
     *
     * @param resId 文字资源
     */
    public static void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(CustomApplication.getInstance(), resId,
                    Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }


}
