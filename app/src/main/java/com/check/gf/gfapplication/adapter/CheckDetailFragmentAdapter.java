package com.check.gf.gfapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.check.gf.gfapplication.fragment.BaseInfoFragment;
import com.check.gf.gfapplication.fragment.DimensionFragment;
import com.check.gf.gfapplication.fragment.PerformanceFragment;
import com.check.gf.gfapplication.fragment.SurfaceFragment;
import com.check.gf.gfapplication.model.IncomeCheck;

/**
 * Created by wqd on 2018/1/3.
 */

public class CheckDetailFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private IncomeCheck mIncomeCheck;
    private static final String[] mTitles = new String[]{"基本信息", "外观", "尺寸", "性能"};

    public CheckDetailFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    public void setBaseInfoData(IncomeCheck incomeCheck) {
        mIncomeCheck = incomeCheck;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return BaseInfoFragment.newInstance(mIncomeCheck);
            case 1:
                return SurfaceFragment.newInstance();
            case 2:
                return DimensionFragment.newInstance();
            case 3:
                return PerformanceFragment.newInstance();
            default:
                return BaseInfoFragment.newInstance(mIncomeCheck);
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
