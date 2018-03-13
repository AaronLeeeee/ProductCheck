package com.check.gf.gfapplication;


import android.content.Intent;
import android.os.Environment;

import com.check.gf.gfapplication.activity.LoginActivity;
import com.check.gf.gfapplication.config.StaticConfig;
import com.check.gf.gfapplication.entity.SearchItem;
import com.check.gf.gfapplication.helper.SharedPreferencesHelper;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.litepal.LitePalApplication;

/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * 自定义全局Application类
 *
 * @author nEdAy
 */
public class CustomApplication extends LitePalApplication {
    // 对外提供整个应用生命周期的Context
    private static CustomApplication mInstance;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private SearchItem searchItem;

    /**
     * 对外提供Application Context
     *
     * @return Application
     */
    public static CustomApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        // Normal app init code...
        mInstance = this;
        if (BuildConfig.LOG_DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            initBugly(true);
        } else {
            initBugly(false);
            Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());
        }
        // 初始化Fresco
        Fresco.initialize(this, StaticConfig.getOkHttpImagePipelineConfig(this));
    }


    /**
     * Beta高级设置
     *
     * @param logDebug 是否开启log
     */
    private void initBugly(boolean logDebug) {
        /**
         * true表示app启动自动初始化升级模块;
         * false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         */
        Beta.autoInit = true;
        /**
         * true表示初始化时自动检查升级;
         * false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = true;
        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 60 * 1000;
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 5 * 1000;
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.mipmap.ic_launcher;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        //Beta.defaultBannerId = R.drawable.ic_update_banner;
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true;
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(LoginActivity.class);
        //统一初始化Bugly产品，包含Beta
        Bugly.init(this, StaticConfig.BUGLY_APP_ID, logDebug);//测试阶段建议设置成true，发布时设置为false。
    }


    /**
     * 单例模式，及时返回SharedPreferences
     *
     * @return SharedPreferences
     */
    public synchronized SharedPreferencesHelper getSpHelper() {
        if (sharedPreferencesHelper == null) {
            sharedPreferencesHelper = new SharedPreferencesHelper(mInstance);
        }
        return sharedPreferencesHelper;
    }

    /**
     * 单例模式，返回SearchItem
     *
     * @return SearchItem
     */
    public synchronized SearchItem getSearchItem() {
        if (searchItem == null) {
            searchItem = new SearchItem();
        }
        return searchItem;
    }


    /**
     * http://www.sixwolf.net/blog/2016/04/11/Android去除烦人的闪退弹窗/
     */
    private class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            Intent intent = new Intent(mInstance, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

}
