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
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.check.gf.gfapplication.BaseActivity;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.CheckListAdapter;
import com.check.gf.gfapplication.base.IBaseListFragment;
import com.check.gf.gfapplication.config.StaticConfig;
import com.check.gf.gfapplication.model.IncomeCheck;
import com.check.gf.gfapplication.utils.ExtendUtils;
import com.check.gf.gfapplication.view.HidingScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

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

    private List<IncomeCheck> mIncomeCheckList = new ArrayList<>();

    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected LinearLayout rl_no_data, rl_no_network;
    protected TextView tv_total;
    protected int mCurrentCounter;
    protected View notLoadingView;
    protected int curPage;
    protected int mCurIndex = -1;
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
        mQuickAdapter.setNewData(mIncomeCheckList);
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
        mRecyclerView.post(this::LoadMoreItem);
    }

    /**
     * 当SwipeRefreshLayout下拉刷新时触发，具体实现在子类
     */
    @Override
    public void onRefresh() {
        RefreshItem();
    }

    /**
     * 点击时修改子控件背景样式并在0.5s后恢复
     *
     * @param view       要修改的子控件
     * @param bg         要恢复的背景
     * @param bg_pressed 要变化的背景
     */
    protected void changePressedViewBg(View view, int bg, int bg_pressed) {
        view.setPressed(true);
        view.setBackgroundResource(bg_pressed);
        Observable.timer(500, TimeUnit.MILLISECONDS) //延迟SHOW_TIME_MIN秒跳转
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    view.setPressed(false);
                    view.setBackgroundResource(bg);
                });
    }

    private final SimpleClickListener SimpleClickListener = new SimpleClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//            Intent item = new Intent(getActivity(),
//                    PortItemDetailsActivity.class);
//            item.putExtra(PortItemDetailsActivity.getExtra(), (PortItem) baseQuickAdapter.getItem(i));
//            startActivity(item);
        }

        @Override
        public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//            PortItem item = mQuickAdapter.getItem(i);
//            AliTradeHelper aliTradeUtils = new AliTradeHelper(getActivity());
//            aliTradeUtils.addToCart(item.getGoodsID());
        }

        @Override
        public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//            PortItem item = mQuickAdapter.getItem(i);
//            AliTradeHelper aliTradeUtils = new AliTradeHelper(getActivity());
//            switch (view.getId()) {
//                case R.id.ll_get:
//                    aliTradeUtils.showItemURLPage(item.getQuan_link());
//                    changePressedViewBg(view, R.drawable.ll_get_bg, R.drawable.ll_get_bg_pressed);
//                    break;
//                case R.id.tx_buy_url:
//                    if (item.isCommission_check()) {
//                        aliTradeUtils.showItemURLPage("http://www.neday.cn/index_.php?r=p/d&id=" + item.getID());
//                    } else {
//                        aliTradeUtils.showItemDetailPage(item.getGoodsID());
//                    }
//                    changePressedViewBg(view, R.drawable.ll_buy_bg, R.drawable.ll_buy_bg_pressed);
//                    break;
//                default:
//                    break;
//            }
        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        }
    };

    public void initAdapter() {
        mQuickAdapter = new CheckListAdapter(new ArrayList<>());
        //mQuickAdapter.addHeaderView(getView());
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.setOnLoadMoreListener(this);
        //mQuickAdapter.openLoadMore(StaticConfig.PAGE_SIZE);
        mQuickAdapter.setAutoLoadMoreSize(StaticConfig.AUTO_SIZE);
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

    /**
     * 当SwipeRefreshLayout下拉刷新时触发
     */
    public void RefreshItem() {
//        Map<String, Object> queryMap = new HashMap<>();
//        toSubscribe(RxFactory.getCheckServiceInstance()
//                        .queryPortItem(queryMap)
//                        .map(PortItem::getResult),
//                () -> {
//                    //隐藏无网络和无数据界面
//                    rl_no_network.setVisibility(View.GONE);
//                    rl_no_data.setVisibility(View.GONE);
//                    curPage = 0;//重置页码
//                    queryMap.put("where", "[{\"key\":\"Title\",\"value\":\"" + searchWhat + "\",\"operation\":\"LIKE\",\"relation\":\"\"}]");
//                    queryMap.put("page", curPage);
//                },
//                portItems -> {
//                    if (portItems.isEmpty()) {
//                        rl_no_data.setVisibility(View.VISIBLE);
//                    } else {
//                        curPage++;
//                    }
//                    mQuickAdapter.setNewData(portItems);
//                    mQuickAdapter.removeAllFooterView();
//                    mCurrentCounter = mQuickAdapter.getData().size();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                },
//                throwable -> {
//                    rl_no_network.setVisibility(View.VISIBLE);
//                    rl_no_network.setOnClickListener(v -> RefreshItem());
//                    mQuickAdapter.getData().clear();
//                    mQuickAdapter.removeAllFooterView();
//                    mCurrentCounter = 0;
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    Logger.e(throwable.getMessage());
//                });
    }

}
