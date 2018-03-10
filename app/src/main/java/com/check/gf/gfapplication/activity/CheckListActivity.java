package com.check.gf.gfapplication.activity;

import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckListAdapter;
import com.check.gf.gfapplication.base.BaseActivity;
import com.check.gf.gfapplication.base.IBaseListFragment;
import com.check.gf.gfapplication.entity.CheckOrder;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.check.gf.gfapplication.view.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测列表页
 *
 * @author nEdAy
 */
public class CheckListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IBaseListFragment, View.OnClickListener {

    private TextView mIncomeCheckTv;
    private TextView mProcessCheckTv;
    private TextView mShipmentsCheckTv;
    private TextView mNcCheckTv;
    private CheckListAdapter mQuickAdapter;

    private List<CheckOrder.DataBean> mCheckOrders = new ArrayList<>();

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected LinearLayout rl_no_data, rl_no_network;
    protected TextView tv_total;
    private TextView tv_now;
    private RelativeLayout rl_top_bar;
    private ImageView fab;

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
        initTopBarForLeft("检测列表", getString(R.string.tx_back));

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
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> mRecyclerView.scrollToPosition(0));
        rl_top_bar = findViewById(R.id.rl_top_bar);
        tv_now = findViewById(R.id.tv_now);
        tv_total = findViewById(R.id.tv_total);
        mRecyclerView.addOnScrollListener(hidingScrollListener);
        initAdapter();
        // 主动刷新数据
        onRefresh();
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
     * RecyclerView滑动监听
     */
    private final HidingScrollListener hidingScrollListener = new HidingScrollListener() {
        @Override
        public void onStart() {
            rl_top_bar.setVisibility(View.VISIBLE);
            rl_top_bar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        }

        @Override
        public void onRemoveAll() {
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            fab.animate()
                    .translationY(dm.heightPixels - fab.getHeight())
                    .setInterpolator(new AccelerateInterpolator(2))
                    .start();
            rl_top_bar.animate()
                    .translationY(dm.heightPixels - rl_top_bar.getHeight())
                    .setInterpolator(new AccelerateInterpolator(2))
                    .start();
        }

        @Override
        public void onMove(int visibleItemNum) {
            tv_now.setText(String.valueOf(visibleItemNum));
        }

        @Override
        public void onHide() {
            rl_top_bar.setVisibility(View.VISIBLE);
            rl_top_bar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            fab.animate()
                    .translationY(dm.heightPixels - fab.getHeight())
                    .setInterpolator(new AccelerateInterpolator(2))
                    .start();
        }

        @Override
        public void onShow() {
            fab.setVisibility(View.VISIBLE);
            fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            rl_top_bar.animate()
                    .translationY(dm.heightPixels - rl_top_bar.getHeight())
                    .setInterpolator(new AccelerateInterpolator(2))
                    .start();
        }
    };


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
//                Intent intent = new Intent(CheckListActivity.this, CheckDetailActivity.class);
//                intent.putExtra(GlobalConstant.IntentConstant.INCOME_CHECK_INFO, incomeCheck);
//                startActivity(intent);
        });
        // 一行代码搞定（默认为渐显效果）
        mQuickAdapter.openLoadAnimation();
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    public void LoadMoreItem() {

    }

    @Override
    public void RefreshItem() {
        CommonUtils.showToast("暂不支持下拉刷新");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
