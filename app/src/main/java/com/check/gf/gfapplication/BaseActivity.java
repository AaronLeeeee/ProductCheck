package com.check.gf.gfapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/**
 * Created by wqd on 2017/12/13.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(savedInstanceState);
        View layoutView = LayoutInflater.from(this).inflate(getContentLayout(), null);
        setContentView(layoutView);
        getIntentData();
        initHeaderView();
        initContentView();
        initFooterView();
        initData();
    }

    protected abstract int getContentLayout();


    protected void getIntentData(Bundle outState) {
    }

    protected void getIntentData() {
    }

    protected void initHeaderView() {
    }

    protected void initContentView() {
    }

    protected void initFooterView() {
    }

    protected void initData() {

    }
}
