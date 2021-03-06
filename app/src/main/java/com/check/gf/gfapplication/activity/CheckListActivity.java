package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckListAdapter;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.base.IBaseList;
import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.entity.SearchItem;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.view.CustomDialog;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测列表页
 *
 * @author nEdAy
 */
public class CheckListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IBaseList, View.OnClickListener, CustomDialog.OnStartQueryEquipmentListener {

    private TextView mIncomeCheckTv;
    private TextView mProcessCheckTv;
    private TextView mShipmentsCheckTv;
    private TextView mNcCheckTv;
    private CheckListAdapter mQuickAdapter;

    private List<CheckOrder> mCheckOrders = new ArrayList<>();

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected LinearLayout rl_no_data, rl_no_network;

    private SearchItem mSearchItem;
    private int mState;

    public static String getExtra() {
        return "CheckOrder";
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_check_list;
    }

    @Override
    public void getIntentData() {
        Intent intent = getIntent();
        mSearchItem = intent.getParcelableExtra(SearchActivity.getSearchItem());
        mState = intent.getIntExtra(SearchActivity.getExtra(), 0);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initTopBarForLeft("检测列表", getString(R.string.tx_back));
        mLoadingView = findViewById(R.id.loadView);

        mIncomeCheckTv = findViewById(R.id.tv_income_check);
        mProcessCheckTv = findViewById(R.id.tv_process_check);
        mShipmentsCheckTv = findViewById(R.id.tv_shipments_check);
        mNcCheckTv = findViewById(R.id.tv_NC_check);

        mIncomeCheckTv.setOnClickListener(this);
        mProcessCheckTv.setOnClickListener(this);
        mShipmentsCheckTv.setOnClickListener(this);
        mNcCheckTv.setOnClickListener(this);

        rl_no_data = findViewById(R.id.rl_no_data);
        rl_no_network = findViewById(R.id.rl_no_network);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
        initCheckContent();
    }


    @Override
    protected void onResume() {
        super.onResume();
        search();
    }

    /**
     * Represents an asynchronous search task
     */
    private void search() {
        mCheckOrders.clear();
        String customerName = mSearchItem.getCustomerName();
        String requireDate = mSearchItem.getmRequireDate();
        String equipmentNo = mSearchItem.getEquipmentNo();
        String docNo = mSearchItem.getDocNo();
        String materialCode = mSearchItem.getMaterialCode();
        String custNo = mSearchItem.getCustNo();
        if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(requireDate) &&
                TextUtils.isEmpty(equipmentNo) && TextUtils.isEmpty(docNo)
                && TextUtils.isEmpty(materialCode) && TextUtils.isEmpty(custNo)) {
            ToastUtils.showShort("程序异常");
            return;
        }
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .CheckOrderQuery(customerName, requireDate,
                                equipmentNo, docNo, materialCode, custNo),
                () -> showLoading("搜索中..."),
                checkOrderResult -> {
                    if (checkOrderResult.getResult() == 0) {
                        hideLoading();
                        List<CheckOrder> checkOrders = checkOrderResult.getData();
                        if (checkOrders == null || checkOrders.size() <= 0) {
                            ToastUtils.showShort("该筛选条件下检验单不存在");
                            return;
                        }
                        for (int i = 0; i < checkOrders.size(); i++) {
                            CheckOrder checkOrder = checkOrders.get(i);
                            int finishState = checkOrder.getFinishState();
                            if (finishState == mState) { // 完成状态 0：未开始  1：检查中  2：检查完成
                                mCheckOrders.add(checkOrder);
                            }
                        }
                        mQuickAdapter.setNewData(mCheckOrders);
                    } else {
                        queryCheckOrderError(checkOrderResult.getDesc());
                    }
                },
                throwable -> queryCheckOrderError(throwable.getMessage()));
    }

    private void queryCheckOrderError(String msg) {
        hideLoading();
        ToastUtils.showShort("二次检索失败，请重试 ：" + msg);
        Logger.e(msg);
        finish();
    }

    private void initCheckContent() {
        mIncomeCheckTv.setText(getString(R.string.income_check, mCheckOrders.size()));
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

    /**
     * 当mRecyclerView上拉加载时触发，具体实现在子类
     */
    @Override
    public void onLoadMoreRequested() {
        LoadMoreItem();
    }

    /**
     * 当SwipeRefreshLayout下拉刷新时触发，具体实现在子类
     */
    @Override
    public void onRefresh() {
        RefreshItem();
    }

    public void initAdapter() {
        mQuickAdapter = new CheckListAdapter(new ArrayList<>());
        //mQuickAdapter.addHeaderView(getView());
        mRecyclerView.setAdapter(mQuickAdapter);
        //条目子控件点击事件
        mQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CheckOrder checkOrder = mCheckOrders.get(position);
            if (0 == mState) { // 完成状态 0：未开始  1：检查中  2：检查完成
                final CustomDialog dialog = new CustomDialog(mContext, position, checkOrder.getEquipmentNo());
                dialog.show();
                dialog.setCanceledOnTouchOutside(true);
            } else {
                queryCheckOrderInfo(checkOrder, checkOrder.getEquipmentNoSecond());
            }
        });
        // 一行代码搞定（默认为渐显效果）
        mQuickAdapter.openLoadAnimation();
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    private void queryCheckOrderInfo(CheckOrder checkOrder, String equipmentNoSecond) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .CheckOrderInfoQuery(checkOrder.getEquipmentNo(), checkOrder.getMaterialCode(), equipmentNoSecond, checkOrder.getDocNo()),
                () -> showLoading("查询详情中..."),
                checkOrderInfoResult -> {
                    if (checkOrderInfoResult.getResult() == 0) {
                        hideLoading();
                        CheckOrderInfo checkOrderInfo = checkOrderInfoResult.getData();
                        Intent intent = new Intent(
                                CheckListActivity.this, CheckDetailActivity.class);
                        intent.putExtra(CheckListActivity.getExtra(), checkOrderInfo);
                        startActivity(intent);
                    } else {
                        queryCheckOrderInfoError(checkOrderInfoResult.getDesc());
                    }
                },
                throwable -> queryCheckOrderInfoError(throwable.getMessage()));
    }

    private void queryCheckOrderInfoError(String msg) {
        hideLoading();
        ToastUtils.showShort("检验单基本信息查询失败，请重试：" + msg);
        Logger.e(msg);
    }

    @Override
    public void LoadMoreItem() {

    }

    @Override
    public void RefreshItem() {
        mSwipeRefreshLayout.setRefreshing(false);
        ToastUtils.showShort("暂页面不支持下拉刷新");
    }

    @Override
    public void onStartQueryEquipmentListener(int position, String equipmentNoSecond) {
        CheckOrder checkOrder = mCheckOrders.get(position);
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .QueryEquipmentNoCheck(checkOrder.getEquipmentNo(), equipmentNoSecond),
                () -> showLoading("查询是否已经检验完成..."),
                result -> {
                    if (result.getResult() == 0) {
                        hideLoading();
                        queryCheckOrderInfo(checkOrder, equipmentNoSecond);
                    } else if (result.getResult() == -1) {
                        queryCheckOrderInfoError("此设备已经检验过，不允许重复检验");
                    } else {
                        queryCheckOrderInfoError(result.getDesc());
                    }
                },
                throwable -> queryCheckOrderInfoError(throwable.getMessage()));
    }

}
