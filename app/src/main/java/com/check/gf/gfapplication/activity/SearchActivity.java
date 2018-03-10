package com.check.gf.gfapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.orhanobut.logger.Logger;

import me.shaohui.bottomdialog.BottomDialog;

/**
 * 搜索主页
 *
 * @author nEdAy
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private TextView mUnStartCheckCountTv, mProcessCheckCountTv, mFinishedCheckCountTv;
    private View mProgressView;
    private View mSearchFormView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initTopBarForLeft("搜索", getString(R.string.tx_exit));
        Button mSearchBt = findViewById(R.id.bt_search);
        //mSearchView = findViewById(R.id.search_view);
        LinearLayout mUnStartCheckLl = findViewById(R.id.ll_unStart);
        mUnStartCheckCountTv = findViewById(R.id.tv_unStart_check);
        LinearLayout mProcessCheckLl = findViewById(R.id.ll_process);
        mProcessCheckCountTv = findViewById(R.id.tv_process_check);
        LinearLayout mFinishedCheckLl = findViewById(R.id.ll_finished);
        mFinishedCheckCountTv = findViewById(R.id.tv_finished_check);
        ExtendUtils.setOnClickListener(this, mSearchBt, mUnStartCheckLl, mProcessCheckLl, mFinishedCheckLl);
    }

    @Override
    protected void initData() {
        super.initData();
        mUnStartCheckCountTv.setText("10");
        mProcessCheckCountTv.setText("20");
        mFinishedCheckCountTv.setText("30");
    }

//    private void initTestDb() {
//        List<IncomeCheck> incomeCheckList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            IncomeCheck incomeCheck = new IncomeCheck();
//            incomeCheck.setId(i);
//            incomeCheck.setClient("测试公司" + i);
//            incomeCheck.setCheckSingleId("IQC_00450569");
//            incomeCheck.setNeedCheckNum(10);
//            incomeCheck.setTotalCheckNum(13);
//            incomeCheck.setMaterialId("1046000014");
//            incomeCheck.setMaterialName("导轨总成");
//            incomeCheck.setPurchaseOrderId("E/300442276.003");
//            incomeCheck.setIncomeCount(1);
//            incomeCheck.setCheckDate("2017-9-3");
//            incomeCheck.setSupplier("马拉兹(江苏)电梯导轨有限公司");
//            incomeCheckList.add(incomeCheck);
//        }
//
//        DataSupport.saveAll(incomeCheckList);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_search:
                BottomDialog.create(getSupportFragmentManager())
                        .setViewListener(this::bindView)
                        .setLayoutRes(R.layout.dialog_layout_search)
                        .setDimAmount(0.2f)   // Dialog window 背景色深度 范围：0 到 1，默认是0.2f
                        .setCancelOutside(true)     // 点击外部区域是否关闭，默认true
                        .setTag("BottomDialog")     // 设置DialogFragment的tag
                        .show();
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

    private void bindView(View view) {
        mLoadingView = view.findViewById(R.id.loadView);
        EditText et_customerName = view.findViewById(R.id.et_customerName);
        TextView tv_requireDate_text = view.findViewById(R.id.tv_requireDate_text);
        EditText et_equipmentNo = view.findViewById(R.id.et_equipmentNo);
        EditText et_docNo = view.findViewById(R.id.et_docNo);
        EditText et_custNo = view.findViewById(R.id.et_custNo);
        view.findViewById(R.id.btn_search).setOnClickListener(view1 -> {
            String customerName = et_customerName.getText().toString().trim();
            String requireDate_text = tv_requireDate_text.getText().toString().trim();
            String equipmentNo = et_equipmentNo.getText().toString().trim();
            String docNo = et_docNo.getText().toString().trim();
            String custNo = et_custNo.getText().toString().trim();
            if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(requireDate_text) &&
                    TextUtils.isEmpty(equipmentNo) && TextUtils.isEmpty(docNo)
                    && TextUtils.isEmpty(custNo)) {
                CommonUtils.showToast("条件不允许同时为空");
            } else {
                search(customerName, requireDate_text, equipmentNo, docNo, custNo);
            }
        });
    }

    /**
     * Represents an asynchronous search task
     *
     * @param customerName
     * @param requireDate_text
     * @param equipmentNo
     * @param docNo
     * @param custNo
     */
    private void search(String customerName, String requireDate_text, String equipmentNo, String docNo, String custNo) {
        // User user = new User(username, password);
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .CheckOrderQuery(new CheckOrder()),
                () -> showProgress(true),
                checkOrderResult -> {
                    if (checkOrderResult.getResult() == 0) {
                        showProgress(false);
                        mUnStartCheckCountTv.setText("");
                        mProcessCheckCountTv.setText("");
                        mFinishedCheckCountTv.setText("");
                    } else {
                        queryCheckOrderError(checkOrderResult.getDesc());
                    }
                },
                throwable -> queryCheckOrderError(throwable.getMessage()));
    }

    private void queryCheckOrderError(String msg) {
        showProgress(false);
        CommonUtils.showToast("");
        Logger.e(msg);
    }

    private void jumpCheckListActivity() {
        startActivity(new Intent(this, CheckListActivity.class));
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mSearchFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    // 连续按两次返回键就退出标记位
    private long firstTime;

    /**
     * 截获Back键动作
     */
    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            CommonUtils.showToast("再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }
}
