package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.check.gf.gfapplication.CustomApplication;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.entity.CheckOrder;
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
    private List<CheckOrder.DataBean> mUnStartCheckOrders = new ArrayList<>();
    private List<CheckOrder.DataBean> mProcessCheckOrders = new ArrayList<>();
    private List<CheckOrder.DataBean> mFinishedCheckOrders = new ArrayList<>();
    private String mRequireDate;
    private BottomDialog mBottomDialog;

    public static String getExtra() {
        return "CheckOrders";
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
        ExtendUtils.setOnClickListener(this, mUnStartCheckLl, mProcessCheckLl, mFinishedCheckLl);
        keepalive(); // todo:fix
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_unStart:
                jumpCheckListActivity(mUnStartCheckOrders);
                break;
            case R.id.ll_process:
                jumpCheckListActivity(mProcessCheckOrders);
                break;
            case R.id.ll_finished:
                jumpCheckListActivity(mFinishedCheckOrders);
                break;
            default:
                break;
        }
    }

    private void initView(View view) {
        EditText et_customerName = view.findViewById(R.id.et_customerName);
        TextView tv_requireDate_text = view.findViewById(R.id.tv_requireDate_text);
        EditText et_equipmentNo = view.findViewById(R.id.et_equipmentNo);
        EditText et_docNo = view.findViewById(R.id.et_docNo);
        EditText et_custNo = view.findViewById(R.id.et_custNo);
        view.findViewById(R.id.btn_search).setOnClickListener(view1 -> {
            String customerName = et_customerName.getText().toString().trim();
            String equipmentNo = et_equipmentNo.getText().toString().trim();
            String docNo = et_docNo.getText().toString().trim();
            String custNo = et_custNo.getText().toString().trim();
            if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(mRequireDate) &&
                    TextUtils.isEmpty(equipmentNo) && TextUtils.isEmpty(docNo)
                    && TextUtils.isEmpty(custNo)) {
                CommonUtils.showToast("条件不允许同时为空");
            } else {
                mBottomDialog.dismiss();
                search(customerName, mRequireDate, equipmentNo, docNo, custNo);
            }
        });
        tv_requireDate_text.setOnClickListener(view12 ->
                initTimePicker().show(tv_requireDate_text));

    }

    private TimePickerView initTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2018, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2018, 2, 15);
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
     * @param customerName 否	string	客户名
     * @param requireDate  否	string	交期限
     * @param equipmentNo  否	string	设备号，也是检验单号
     * @param docNo        否	string	出货计划单
     * @param custNo       否	string	客户订单号
     */
    private void search(String customerName, String requireDate, String equipmentNo, String docNo, String custNo) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .CheckOrderQuery(customerName, requireDate,
                                equipmentNo, docNo, custNo),
                () -> showLoading("搜索中..."),
                checkOrderResult -> {
                    if (checkOrderResult.getResult() == 0) {
                        hideLoading();
                        List<CheckOrder.DataBean> checkOrders = checkOrderResult.getData();
                        if (checkOrders == null || checkOrders.size() <= 0) {
                            CommonUtils.showToast("该筛选条件下检验单不存在");
                            return;
                        }
                        mUnStartCheckOrders.clear();
                        mProcessCheckOrders.clear();
                        mFinishedCheckOrders.clear();
                        for (int i = 0; i < checkOrders.size(); i++) {
                            CheckOrder.DataBean checkOrder = checkOrders.get(i);
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
                    } else {
                        queryCheckOrderError(checkOrderResult.getDesc());
                    }
                },
                throwable -> queryCheckOrderError(throwable.getMessage()));
    }


    /**
     * Represents an asynchronous keep alive
     */
    private void keepalive() {
        toSubscribe(RxFactory.getPublicServiceInstance()
                        .keepalive(),
                () -> showLoading("登录中..."),
                result -> {
                    if ("forever".equals(result.getExpiration())) {
                        hideLoading();
                    } else {
                        queryKeepalveError("error");
                    }
                },
                throwable -> queryKeepalveError(throwable.getMessage()));
    }

    private void queryKeepalveError(String msg) {
        finish();
    }

    private void queryCheckOrderError(String msg) {
        hideLoading();
        CommonUtils.showToast(msg);
        Logger.e(msg);
    }

    // todo: 搜索 记录  并且刷新
    // todo: 开始检查 loading 黑
    private void jumpCheckListActivity(List<CheckOrder.DataBean> mCheckOrders) {
        if (mCheckOrders.size() > 0) {
            Intent intent = new Intent(this, CheckListActivity.class);
            intent.putParcelableArrayListExtra(SearchActivity.getExtra(), (ArrayList<? extends Parcelable>) mCheckOrders);
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
