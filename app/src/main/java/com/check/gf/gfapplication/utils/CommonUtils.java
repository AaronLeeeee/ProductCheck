package com.check.gf.gfapplication.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
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
     * 检查是否是移动网络
     */
    public static boolean isMobile() {
        NetworkInfo info = getNetworkInfo(CustomApplication.getInstance());
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    /**
     * 启动到app应用商店详情界面
     * <p>
     * 主流应用商店对应的包名如下：
     * com.qihoo.appstore  360手机助手
     * com.taobao.appcenter    淘宝手机助手
     * com.tencent.android.qqdownloader    应用宝
     * com.hiapk.marketpho 安卓市场
     * cn.goapk.market 安智市场
     *
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context mContext, String marketPkg) {
        try {
            Uri uri = Uri.parse("market://details?id=" + mContext.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    /**
     * 指定View显示一个动画,抖5下
     *
     * @param view 指定的View
     */
    public static void setShakeAnimation(View view) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 10);
        translateAnimation.setInterpolator(new CycleInterpolator(5));//抖5下
        translateAnimation.setDuration(1000);
        view.startAnimation(translateAnimation);
    }


    /**
     * dip to px
     *
     * @param mContext 上下文
     * @param dipValue dip值
     * @return 对应px值
     */
    public static int dip2px(Context mContext, float dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    /**
     * 震动 注意申请权限
     *
     * @param activity     当前activity
     * @param milliseconds 震动时间 单位毫秒
     */
    public static void Vibrate(final Activity activity, long milliseconds) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(milliseconds);
        }
    }

}
