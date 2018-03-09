package com.check.gf.gfapplication.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import com.check.gf.gfapplication.BaseActivity;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.model.IncomeCheck;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.check.gf.gfapplication.view.SearchView;

/**
 * 搜索页
 *
 * Created by wqd on 2017/12/18.
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private SearchView mSearchView;
    private Button mSearchBt;
    private LinearLayout mUnStartCheckLl;
    private TextView mUnStartCheckCountTv;
    private LinearLayout mProcessCheckLl;
    private TextView mProcessCheckCountTv;
    private LinearLayout mFinishedCheckLl;
    private TextView mFinishedCheckCountTv;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mSearchBt = (Button) findViewById(R.id.bt_search);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mUnStartCheckLl = (LinearLayout) findViewById(R.id.ll_unStart);
        mUnStartCheckCountTv = (TextView) findViewById(R.id.tv_unStart_check);
        mProcessCheckLl = (LinearLayout) findViewById(R.id.ll_process);
        mProcessCheckCountTv = (TextView) findViewById(R.id.tv_process_check);
        mFinishedCheckLl = (LinearLayout) findViewById(R.id.ll_finished);
        mFinishedCheckCountTv = (TextView) findViewById(R.id.tv_finished_check);
        ExtendUtils.setOnClickListener(this, mSearchBt, mUnStartCheckLl, mProcessCheckLl, mFinishedCheckLl);
    }

    @Override
    protected void initData() {
        super.initData();
//        initTestDb();
    }

    private void initTestDb() {
        List<IncomeCheck> incomeCheckList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            IncomeCheck incomeCheck = new IncomeCheck();
            incomeCheck.setId(i);
            incomeCheck.setClient("测试公司" + i);
            incomeCheck.setCheckSingleId("IQC_00450569");
            incomeCheck.setNeedCheckNum(10);
            incomeCheck.setTotalCheckNum(13);
            incomeCheck.setMaterialId("1046000014");
            incomeCheck.setMaterialName("导轨总成");
            incomeCheck.setPurchaseOrderId("E/300442276.003");
            incomeCheck.setIncomeCount(1);
            incomeCheck.setCheckDate("2017-9-3");
            incomeCheck.setSupplier("马拉兹(江苏)电梯导轨有限公司");
            incomeCheckList.add(incomeCheck);
        }

        DataSupport.saveAll(incomeCheckList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                mSearchView.showSearchView();
                break;
            case R.id.ll_unStart:
                jumpCheckListActivity();
                break;
            case R.id.ll_process:
                jumpCheckListActivity();
                break;
            case R.id.ll_finished:
                jumpCheckListActivity();
                break;
            default:
                break;
        }
    }

    private void jumpCheckListActivity() {
        startActivity(new Intent(this, CheckListActivity.class));
    }
}
