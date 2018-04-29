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
public class CheckDetailActivity extends BaseActivity implements BaseInfoFragment.onTestBeginListener {
    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    private static CheckDetailActivity mInstance;
    private CheckOrderInfo mCheckOrderInfo;
    private GuardViewPager vp_paper;
    private SlidingTabLayout tl_library;
    private String equipmentNoSecond;

    /**
     * 对外提供CheckDetailActivity Context
     *
     * @return CheckDetailActivity
     */
    public static CheckDetailActivity getInstance() {
        return mInstance;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_detail;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mInstance = this;
        initTopBarForLeft("检测单详情", getString(R.string.tx_back));
        vp_paper = findViewById(R.id.vpItemLeftPaper);
        vp_paper.setOffscreenPageLimit(1);
        List<String> titles = new ArrayList<>();
        titles.add("基本信息");
        mCheckOrderInfo = getIntent().getParcelableExtra(CheckListActivity.getExtra());
        mFragments.add(BaseInfoFragment.newInstance(mCheckOrderInfo));
        List<CheckOrderInfo.CheckDataBean> checkDataBeanList = mCheckOrderInfo.getCheckData();
        if (checkDataBeanList != null) {
            int size = checkDataBeanList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    CheckOrderInfo.CheckDataBean checkDataBean = checkDataBeanList.get(i);
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
        onTestBegin(false);
    }


    /**
     * 接受事件，切换点击/滑动状态
     *
     * @param enable 是否开启点击/滑动状态
     */
    @Override
    public void onTestBegin(boolean enable) {
        vp_paper.setSlidingEnable(enable);
        tl_library.setTabClickEnable(enable);
    }


    public synchronized String getEquipmentNoSecond() {
        return equipmentNoSecond;
    }


    public synchronized void setEquipmentNoSecond(String equipmentNoSecond) {
        this.equipmentNoSecond = equipmentNoSecond;
    }


}
