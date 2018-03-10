package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.fragment.BaseInfoFragment;
import com.check.gf.gfapplication.fragment.DimensionFragment;
import com.check.gf.gfapplication.fragment.PerformanceFragment;
import com.check.gf.gfapplication.fragment.SurfaceFragment;
import com.check.gf.gfapplication.view.GuardViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * 检测详情页
 *
 * @author nEdAy
 */
public class CheckDetailActivity extends BaseActivity {
    private final static int INFO_FRAGMENT = 0;
    private final static int DIMENSION_FRAGMENT = 1;
    private final static int PERFORMANCE_FRAGMENT = 2;
    private final static int SURFACE_FRAGMENT = 3;
    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    private CheckOrderInfo.DataBean mCheckOrderInfo;

    @Override
    protected void getIntentData() {
        super.getIntentData();
        Intent intent = getIntent();
        mCheckOrderInfo = intent.getParcelableExtra(CheckListActivity.getExtra());
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_detail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        GuardViewPager vp_paper = findViewById(R.id.vpItemLeftPaper);
        vp_paper.setOffscreenPageLimit(10);
        mFragments.add(BaseInfoFragment.newInstance(mCheckOrderInfo));
        mFragments.add(DimensionFragment.newInstance());
        mFragments.add(PerformanceFragment.newInstance());
        mFragments.add(SurfaceFragment.newInstance());
        ((SlidingTabLayout) findViewById(R.id.tl_library))
                .setViewPager(vp_paper, getResources().getStringArray(R.array.inspect_type_name), this, mFragments);
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
