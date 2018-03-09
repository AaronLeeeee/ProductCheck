package com.check.gf.gfapplication.activity;

import android.content.Intent;

import com.check.gf.gfapplication.BaseActivity;
import com.check.gf.gfapplication.R;

/**
 * Created by wqd on 2017/12/13.
 */

public class LaunchActivity extends BaseActivity {


    @Override
    protected int getContentLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initData() {
        super.initData();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
