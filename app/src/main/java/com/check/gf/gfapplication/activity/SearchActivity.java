package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.check.gf.gfapplication.CustomApplication;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.entity.SearchItem;
import com.check.gf.gfapplication.helper.SharedPreferencesHelper;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.shaohui.bottomdialog.BottomDialog;

/**
 * 搜索主页
 *
 * @author nEdAy
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private TextView mUnStartCheckCountTv, mProcessCheckCountTv, mFinishedCheckCountTv;
    private List<CheckOrder> mUnStartCheckOrders = new ArrayList<>();
    private List<CheckOrder> mProcessCheckOrders = new ArrayList<>();
    private List<CheckOrder> mFinishedCheckOrders = new ArrayList<>();
    private String mRequireDate;
    private BottomDialog mBottomDialog;
    private SearchItem mSearchItem = new SearchItem();
    private boolean isNotFirstInit;

    public static String getExtra() {
        return "CheckOrders";
    }

    public static String getSearchItem() {
        return "SearchItem";
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        mBottomDialog = BottomDialog.create(getSupportFragmentManager())
                .setViewListener(this::initView)
                .setLayoutRes(R.layout.dialog_layout_search)
                .setDimAmount(0.2f)   // Dialog window 背景色深度 范围：0 到 1，默认是0.2f
                .setCancelOutside(true)     // 点击外部区域是否关闭，默认true
                .setTag("BottomDialog");
        initTopBarForBoth(getString(R.string.tx_search), getString(R.string.tx_logout), () -> {
            SharedPreferencesHelper sharedPreferencesHelper = CustomApplication.getInstance().getSpHelper();
            sharedPreferencesHelper.clear();
            CommonUtils.showToast("注销成功！");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, getString(R.string.tx_filter), mBottomDialog::show);
        mLoadingView = findViewById(R.id.loadView);
        LinearLayout mUnStartCheckLl = findViewById(R.id.ll_unStart);
        mUnStartCheckCountTv = findViewById(R.id.tv_unStart_check);
        LinearLayout mProcessCheckLl = findViewById(R.id.ll_process);
        mProcessCheckCountTv = findViewById(R.id.tv_process_check);
        LinearLayout mFinishedCheckLl = findViewById(R.id.ll_finished);
        mFinishedCheckCountTv = findViewById(R.id.tv_finished_check);
        findViewById(R.id.btn_search).setOnClickListener(view -> mBottomDialog.show());
        ExtendUtils.setOnClickListener(this, mUnStartCheckLl, mProcessCheckLl, mFinishedCheckLl);
        //keepAlive();
    }


    @Override
    protected void onResume() {
        super.onResume();
        String customerName = mSearchItem.getCustomerName();
        String requireDate = mSearchItem.getmRequireDate();
        String equipmentNo = mSearchItem.getEquipmentNo();
        String docNo = mSearchItem.getDocNo();
        String materialCode = mSearchItem.getMaterialCode();
        String custNo = mSearchItem.getCustNo();
        if (!isNotFirstInit) {
            isNotFirstInit = true;
            return;
        }
        if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(requireDate) &&
                TextUtils.isEmpty(equipmentNo) && TextUtils.isEmpty(docNo)
                && TextUtils.isEmpty(materialCode) && TextUtils.isEmpty(custNo)) {
            return;
        }
        search(customerName, equipmentNo, requireDate, docNo, materialCode, custNo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_unStart:
                jumpCheckListActivity(mUnStartCheckOrders, 0);
                break;
            case R.id.ll_process:
                jumpCheckListActivity(mProcessCheckOrders, 1);
                break;
            case R.id.ll_finished:
                jumpCheckListActivity(mFinishedCheckOrders, 2);
                break;
            default:
                break;
        }
    }

    private void initView(View view) {
        EditText et_customerName = view.findViewById(R.id.et_customerName);
        et_customerName.setText(mSearchItem.getCustomerName());
        TextView tv_requireDate_text = view.findViewById(R.id.tv_requireDate_text);
        String requireDate = mSearchItem.getmRequireDate();
        tv_requireDate_text.setText(TextUtils.isEmpty(requireDate) ? getString(R.string.tv_please_pick) : requireDate);
        ImageView iv_btn_clean = view.findViewById(R.id.iv_btn_clean);
        iv_btn_clean.setOnClickListener(view13 -> {
            mRequireDate = "";
            mSearchItem.setmRequireDate("");
            tv_requireDate_text.setText(getString(R.string.tv_please_pick));
        });
        EditText et_equipmentNo = view.findViewById(R.id.et_equipmentNo);
        et_equipmentNo.setText(mSearchItem.getEquipmentNo());
        EditText et_docNo = view.findViewById(R.id.et_docNo);
        et_docNo.setText(mSearchItem.getDocNo());
        EditText et_materialCode = view.findViewById(R.id.et_materialCode);
        et_materialCode.setText(mSearchItem.getMaterialCode());
        EditText et_custNo = view.findViewById(R.id.et_custNo);
        et_custNo.setText(mSearchItem.getCustNo());
        view.findViewById(R.id.btn_search).setOnClickListener(view1 -> {
            String customerName = et_customerName.getText().toString().trim();
            String equipmentNo = et_equipmentNo.getText().toString().trim();
            String docNo = et_docNo.getText().toString().trim();
            String materialCode = et_materialCode.getText().toString().trim();
            String custNo = et_custNo.getText().toString().trim();
            mBottomDialog.dismiss();
            if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(mRequireDate) &&
                    TextUtils.isEmpty(equipmentNo) && TextUtils.isEmpty(docNo)
                    && TextUtils.isEmpty(materialCode) && TextUtils.isEmpty(custNo)) {
                CommonUtils.showToast("条件不允许同时为空");
                return;
            }
            search(customerName, equipmentNo, mRequireDate, docNo, materialCode, custNo);
        });
        tv_requireDate_text.setOnClickListener(view12 ->
                initTimePicker().show(tv_requireDate_text));

    }

    private TimePickerView initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2030, 0, 1);
        // 时间选择器
        return new TimePickerView.Builder(this,
                (date, view) -> {//选中事件回调
                    // 这里回调过来的v,就是show()方法里面所添加的 View 参数，
                    // 如果show的时候没有添加参数，view则为null
                    TextView tv_requireDate_text = (TextView) view;
                    mRequireDate = getTime(date);
                    tv_requireDate_text.setText(mRequireDate);
                })
                // 年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .isDialog(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setDecorView(null)
                .build();
    }

    private String getTime(Date date) { // 可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }

    /**
     * Represents an asynchronous search task
     *
     * @param customerName
     * @param equipmentNo
     * @param requireDate
     * @param docNo
     * @param materialCode
     * @param custNo
     */
    private void search(String customerName, String equipmentNo, String requireDate, String docNo, String materialCode, String custNo) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .CheckOrderQuery(customerName, requireDate,
                                equipmentNo, docNo, materialCode, custNo),
                () -> showLoading("搜索中..."),
                checkOrderResult -> {
                    if (checkOrderResult.getResult() == 0) {
                        hideLoading();
                        List<CheckOrder> checkOrders = checkOrderResult.getData();
                        if (checkOrders == null || checkOrders.size() <= 0) {
                            CommonUtils.showToast("该筛选条件下检验单不存在,请重新搜索");
                            return;
                        }
                        mUnStartCheckOrders.clear();
                        mProcessCheckOrders.clear();
                        mFinishedCheckOrders.clear();
                        for (int i = 0; i < checkOrders.size(); i++) {
                            CheckOrder checkOrder = checkOrders.get(i);
                            int finishState = checkOrder.getFinishState();
                            if (finishState == 0) { // 完成状态 0：未开始  1：检查中  2：检查完成
                                mUnStartCheckOrders.add(checkOrder);
                            } else if (finishState == 1) {
                                mProcessCheckOrders.add(checkOrder);
                            } else if (finishState == 2) {
                                mFinishedCheckOrders.add(checkOrder);
                            } else {
                                CommonUtils.showToast("存在异常数据");
                            }
                        }
                        mUnStartCheckCountTv.setText(String.valueOf(mUnStartCheckOrders.size() + " 单"));
                        mProcessCheckCountTv.setText(String.valueOf(mProcessCheckOrders.size() + " 单"));
                        mFinishedCheckCountTv.setText(String.valueOf(mFinishedCheckOrders.size() + " 单"));
                        mSearchItem.setCustomerName(customerName);
                        mSearchItem.setEquipmentNo(equipmentNo);
                        mSearchItem.setmRequireDate(mRequireDate);
                        mSearchItem.setDocNo(docNo);
                        mSearchItem.setMaterialCode(materialCode);
                        mSearchItem.setCustNo(custNo);
                    } else {
                        queryCheckOrderError(checkOrderResult.getDesc());
                    }
                },
                throwable -> queryCheckOrderError(throwable.getMessage()));
    }


    /**
     * Represents an asynchronous keep alive
     */
    private void keepAlive() {
        toSubscribe(RxFactory.getPublicServiceInstance()
                        .keepalive(),
                () -> showLoading("登录中..."),
                result -> {
                    if ("forever".equals(result.getExpiration())) {
                        hideLoading();
                    } else {
                        queryKeepAliveError("error");
                    }
                },
                throwable -> queryKeepAliveError(throwable.getMessage()));
    }

    private void queryKeepAliveError(String msg) {
        finish();
        startActivity(new Intent(
                SearchActivity.this, LoginActivity.class));
        Logger.e(msg);
    }

    private void queryCheckOrderError(String msg) {
        hideLoading();
        CommonUtils.showToast(msg);
        Logger.e(msg);
    }

    private void jumpCheckListActivity(List<CheckOrder> mCheckOrders, int checkOrderState) {
        if (mCheckOrders.size() > 0) {
            Intent intent = new Intent(this, CheckListActivity.class);
            intent.putExtra(SearchActivity.getExtra(), checkOrderState);
            intent.putExtra(SearchActivity.getSearchItem(), mSearchItem);
            startActivity(intent);
        } else {
            CommonUtils.showToast("没有该状态的检验单");
        }
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
