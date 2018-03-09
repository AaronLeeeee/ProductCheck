package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.check.gf.gfapplication.BaseActivity;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckDetailFragmentAdapter;
import com.check.gf.gfapplication.config.GlobalConstant;
import com.check.gf.gfapplication.model.IncomeCheck;

/**
 * 检测详情页
 *
 * @author nEdAy
 */
public class CheckDetailActivity extends BaseActivity {

    private IncomeCheck mIncomeCheck;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private CheckDetailFragmentAdapter mCheckDetailFragmentAdapter;

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Intent intent = getIntent();
        mIncomeCheck = (IncomeCheck) intent.getSerializableExtra(GlobalConstant.IntentConstant.INCOME_CHECK_INFO);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_detail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mTabLayout = findViewById(R.id.tab_title);
        mViewPager = findViewById(R.id.vp_detail);
        mCheckDetailFragmentAdapter = new CheckDetailFragmentAdapter(getSupportFragmentManager(), this);
        mCheckDetailFragmentAdapter.setBaseInfoData(mIncomeCheck);
        mViewPager.setAdapter(mCheckDetailFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
