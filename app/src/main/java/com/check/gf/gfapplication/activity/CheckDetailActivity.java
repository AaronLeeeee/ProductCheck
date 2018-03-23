package com.check.gf.gfapplication.activity;

import android.support.v4.app.Fragment;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.fragment.BaseInfoFragment;
import com.check.gf.gfapplication.fragment.InspectListFragment;
import com.check.gf.gfapplication.view.GuardViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测详情页
 *
 * @author nEdAy
 */
public class CheckDetailActivity extends BaseActivity implements BaseInfoFragment.onTestListener {
    private final static int INFO_FRAGMENT = 0;
    private final static String DIMENSION_FRAGMENT = "001";
    private final static String PERFORMANCE_FRAGMENT = "002";
    private final static String SURFACE_FRAGMENT = "003";
    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    private CheckOrderInfo.DataBean mCheckOrderInfo;
    private GuardViewPager vp_paper;
    private SlidingTabLayout tl_library;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_detail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initTopBarForLeft("检测单详情", getString(R.string.tx_back));
        vp_paper = findViewById(R.id.vpItemLeftPaper);
        vp_paper.setOffscreenPageLimit(1);
        List<String> titles = new ArrayList<>();
        titles.add("基本信息");
        mCheckOrderInfo = getIntent().getParcelableExtra(CheckListActivity.getExtra());
        mFragments.add(BaseInfoFragment.newInstance(mCheckOrderInfo));
        List<CheckOrderInfo.DataBean.CheckDataBean> checkDataBeanList = mCheckOrderInfo.getCheckData();
        if (checkDataBeanList != null) {
            int size = checkDataBeanList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    CheckOrderInfo.DataBean.CheckDataBean checkDataBean = checkDataBeanList.get(i);
                    if (checkDataBean != null) {
                        mFragments.add(InspectListFragment.newInstance(checkDataBean.getInspectCode(), mCheckOrderInfo.getEquipmentNo(), mCheckOrderInfo.getMaterialCode()));
                        titles.add(checkDataBean.getTypeName());
                    }
                }
            }
        }
        String[] fTitles = titles.toArray(new String[titles.size()]);
        tl_library = findViewById(R.id.tl_library);
        tl_library.setViewPager(vp_paper, fTitles, this, mFragments);
        onTest(false);
    }


    /**
     * 接受事件，切换点击/滑动状态
     *
     * @param enable 是否开启点击/滑动状态
     */
    @Override
    public void onTest(boolean enable) {
        vp_paper.setSlidingEnable(enable);
        tl_library.setTabClickEnable(enable);
    }
}
