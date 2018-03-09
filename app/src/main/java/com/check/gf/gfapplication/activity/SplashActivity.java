package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.check.gf.gfapplication.entity.User;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 启动页
 *
 * @author nEdAy
 */
public class SplashActivity extends AppCompatActivity {
    private static final int SHOW_TIME_MIN = 0; //0s
    private static final int GO_LOGIN = 0; //导航页
    private static final int GO_MAIN = 1; //主页面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 为保证启动速度，SplashActivity不要调用setContentView()方法
        // setContentView(R.layout.activity_splash);
        Observable.timer(SHOW_TIME_MIN, TimeUnit.MILLISECONDS) //延迟SHOW_TIME_MIN秒跳转
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> doStartActivity(initAndChoosePath()));

    }

    /**
     * 初始化操作并检测是否是第一次启动
     *
     * @return 页面序数
     */
    private int initAndChoosePath() {
        if (User.getCurrentUser() == null) {
            return GO_LOGIN;
        } else {
            return GO_MAIN;
        }
    }

    /**
     * 跳转指定Activity
     *
     * @param result 页面序数
     */
    private void doStartActivity(int result) {
        switch (result) {
            case GO_LOGIN:
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                break;
            case GO_MAIN:
                startActivity(new Intent(SplashActivity.this, SearchActivity.class));
                break;
            default:
                break;
        }
        finish();
    }

    /**
     * SplashActivity屏蔽物理返回按钮
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

}
