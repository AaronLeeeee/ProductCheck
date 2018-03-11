package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.fragment.BaseInfoFragment;
import com.check.gf.gfapplication.fragment.InspectListFragment;
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
    private final static String DIMENSION_FRAGMENT = "001";
    private final static String PERFORMANCE_FRAGMENT = "002";
    private final static String SURFACE_FRAGMENT = "003";
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
        initTopBarForLeft("检测单详情", getString(R.string.tx_back));
        GuardViewPager vp_paper = findViewById(R.id.vpItemLeftPaper);
        vp_paper.setOffscreenPageLimit(1);
        mFragments.add(BaseInfoFragment.newInstance(mCheckOrderInfo));
        mFragments.add(InspectListFragment.newInstance(DIMENSION_FRAGMENT, mCheckOrderInfo.getEquipmentNo()));
        mFragments.add(InspectListFragment.newInstance(PERFORMANCE_FRAGMENT, mCheckOrderInfo.getEquipmentNo()));
        mFragments.add(InspectListFragment.newInstance(SURFACE_FRAGMENT, mCheckOrderInfo.getEquipmentNo()));
        ((SlidingTabLayout) findViewById(R.id.tl_library))
                .setViewPager(vp_paper, getResources().getStringArray(R.array.inspect_type_name), this, mFragments);
    }

    @Override
    protected void initData() {
        super.initData();
    }

}
