package com.check.gf.gfapplication.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckListAdapter;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.base.IBaseList;
import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测列表页
 *
 * @author nEdAy
 */
public class CheckListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IBaseList, View.OnClickListener {

    private TextView mIncomeCheckTv;
    private TextView mProcessCheckTv;
    private TextView mShipmentsCheckTv;
    private TextView mNcCheckTv;
    private CheckListAdapter mQuickAdapter;

    private List<CheckOrder.DataBean> mCheckOrders = new ArrayList<>();

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected LinearLayout rl_no_data, rl_no_network;

    public static String getExtra() {
        return "CheckOrder";
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_check_list;
    }

    @Override
    protected void initContentView() {
        super.initContentView();
        initTopBarForLeft("检测列表", getString(R.string.tx_back));
        mLoadingView = findViewById(R.id.loadView);

        mIncomeCheckTv = findViewById(R.id.tv_income_check);
        mProcessCheckTv = findViewById(R.id.tv_process_check);
        mShipmentsCheckTv = findViewById(R.id.tv_shipments_check);
        mNcCheckTv = findViewById(R.id.tv_NC_check);

        ExtendUtils.setOnClickListener(this, mIncomeCheckTv, mProcessCheckTv, mShipmentsCheckTv, mNcCheckTv);

        rl_no_data = findViewById(R.id.rl_no_data);
        rl_no_network = findViewById(R.id.rl_no_network);
        mRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
    }


    @Override
    protected void initData() {
        super.initData();
        mCheckOrders = getIntent().getParcelableArrayListExtra(SearchActivity.getExtra());
        mQuickAdapter.setNewData(mCheckOrders);
        initCheckContent();
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
            queryCheckOrderInfo(mCheckOrders.get(position));

        });
        // 一行代码搞定（默认为渐显效果）
        mQuickAdapter.openLoadAnimation();
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    private void queryCheckOrderInfo(CheckOrder.DataBean checkOrder) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .CheckOrderInfoQuery(checkOrder.getEquipmentNo()),
                () -> showLoading("查询详情中..."),
                checkOrderInfoResult -> {
                    if (checkOrderInfoResult.getResult() == 0) {
                        hideLoading();
                        CheckOrderInfo.DataBean checkOrderInfo = checkOrderInfoResult.getData();
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
        CommonUtils.showToast("检验单基本信息查询失败，请重试：" + msg);
        Logger.e(msg);
    }

    @Override
    public void LoadMoreItem() {

    }

    @Override
    public void RefreshItem() {
        mSwipeRefreshLayout.setRefreshing(false);
        CommonUtils.showToast("暂页面不支持下拉刷新");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
