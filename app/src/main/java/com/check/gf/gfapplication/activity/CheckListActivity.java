package com.check.gf.gfapplication.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.check.gf.gfapplication.BaseActivity;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckListAdapter;
import com.check.gf.gfapplication.config.GlobalConstant;
import com.check.gf.gfapplication.model.IncomeCheck;
import com.check.gf.gfapplication.utils.ExtendUtils;

/**
 * 检测列表页
 *
 * Created by wqd on 2017/12/18.
 */

public class CheckListActivity extends BaseActivity implements View.OnClickListener, CheckListAdapter.GoDetailClickListener {

    private TextView mIncomeCheckTv;
    private TextView mProcessCheckTv;
    private TextView mShipmentsCheckTv;
    private TextView mNcCheckTv;
    private CheckListAdapter mCheckListAdapter;

    private List<IncomeCheck> mIncomeCheckList = new ArrayList<>();

    @Override
    protected void getIntentData() {
        super.getIntentData();

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_list;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mIncomeCheckTv = (TextView) findViewById(R.id.tv_income_check);
        mProcessCheckTv = (TextView) findViewById(R.id.tv_process_check);
        mShipmentsCheckTv = (TextView) findViewById(R.id.tv_shipments_check);
        mNcCheckTv = (TextView) findViewById(R.id.tv_NC_check);
        ListView checkListLv = (ListView) findViewById(R.id.lv_check_list);
        mCheckListAdapter = new CheckListAdapter(this);
        mCheckListAdapter.setGoDetailClickListener(this);
        checkListLv.setAdapter(mCheckListAdapter);
        ExtendUtils.setOnClickListener(this, mIncomeCheckTv, mProcessCheckTv, mShipmentsCheckTv, mNcCheckTv);
    }

    @Override
    protected void initData() {
        super.initData();
        mIncomeCheckList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            IncomeCheck incomeCheck = new IncomeCheck();
            incomeCheck.checkSingleId = "IQC_00450569";
            incomeCheck.needCheckNum = 10;
            incomeCheck.totalCheckNum = 13;
            incomeCheck.materialId = "1046000014";
            incomeCheck.materialName = "导轨总成";
            incomeCheck.purchaseOrderId = "E/300442276.003";
            incomeCheck.incomeCount = 1;
            incomeCheck.checkDate = "2017-9-3";
            incomeCheck.supplier = "马拉兹(江苏)电梯导轨有限公司";
            mIncomeCheckList.add(incomeCheck);
        }
        initCheckContent();
        mCheckListAdapter.bindData(mIncomeCheckList);
    }

    private void initCheckContent() {
        mIncomeCheckTv.setText(getString(R.string.income_check, mIncomeCheckList.size()));
        mProcessCheckTv.setText(getString(R.string.process_check, 0));
        mShipmentsCheckTv.setText(getString(R.string.shipments_check, 0));
        mNcCheckTv.setText(getString(R.string.nc_check, 0));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_income_check:
                break;
            case R.id.tv_process_check:
                break;
            case R.id.tv_shipments_check:
                break;
            case R.id.tv_NC_check:
                break;
            default:
                break;
        }
    }

    @Override
    public void goDetail(IncomeCheck incomeCheck) {
        Intent intent = new Intent(this, CheckDetailActivity.class);
        intent.putExtra(GlobalConstant.IntentConstant.INCOME_CHECK_INFO, incomeCheck);
        startActivity(intent);
    }
}
