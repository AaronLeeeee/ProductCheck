package com.check.gf.gfapplication.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.InspectListAdapter;
import com.check.gf.gfapplication.base.BaseFragment;
import com.check.gf.gfapplication.base.IBaseList;
import com.check.gf.gfapplication.view.HidingScrollListener;

import java.util.ArrayList;

/**
 * 尺寸
 *
 * @author nEdAy
 */
public class DimensionFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IBaseList {
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected LinearLayout rl_no_data, rl_no_network;
    protected TextView tv_total;
    protected int mCurrentCounter;
    protected View notLoadingView;
    protected int curPage;
    private InspectListAdapter mQuickAdapter;
    protected int mCurIndex = -1;
    private TextView tv_now;
    private RelativeLayout rl_top_bar;
    private ImageView fab;

    public static DimensionFragment newInstance() {
        Bundle args = new Bundle();
        DimensionFragment fragment = new DimensionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dimension, container, false);
        setUpViews(layout);
        return layout;
    }

    public void initAdapter() {
        //获得索引值
        Bundle bundle = getArguments();
        if (bundle != null) {
            //mCurIndex = bundle.getInt(FRAGMENT_INDEX);
        }
        mQuickAdapter = new InspectListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mQuickAdapter);
        // 一行代码搞定（默认为渐显效果）
        mQuickAdapter.openLoadAnimation();
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    /**
     * 当SwipeRefreshLayout下拉刷新时触发
     */
    public void RefreshItem() {
//        Map<String, Object> queryMap = new HashMap<>();
//        toSubscribe(RxFactory.getPortItemServiceInstance(null)
//                        .queryPortItem(queryMap)
//                        .map(PortItem::getResult),
//                () -> {
//                    //隐藏无网络和无数据界面
//                    rl_no_network.setVisibility(View.GONE);
//                    rl_no_data.setVisibility(View.GONE);
//                    curPage = 0;//重置页码
//                    int type = mCurIndex + 1;//页面序数0-2，而大淘客分页1-3（今日新品，超级人气榜，特卖精选），故+1;
//                    queryMap.put("type", type);
//                },
//                portItems -> {
//                    if (portItems.isEmpty()) {
//                        rl_no_data.setVisibility(View.VISIBLE);
//                        rl_no_data.setOnClickListener(v -> RefreshItem());
//                    } else {
//                        curPage++;
//                    }
//                    mQuickAdapter.setNewData(portItems);
//                    mCurrentCounter = mQuickAdapter.getData().size();
//                    mQuickAdapter.removeAllFooterView();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                },
//                throwable -> {
//                    rl_no_network.setVisibility(View.VISIBLE);
//                    rl_no_network.setOnClickListener(v -> RefreshItem());
//                    mQuickAdapter.getData().clear();
//                    mCurrentCounter = 0;
//                    mQuickAdapter.removeAllFooterView();
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    Logger.e(throwable.getMessage());
//                });
    }

    /**
     * 当mRecyclerView上拉加载时触发
     */
    public void LoadMoreItem() {
//        Map<String, Object> queryMap = new HashMap<>();
//        toSubscribe(RxFactory.getPortItemServiceInstance(null)
//                        .queryPortItem(queryMap),
//                () -> {
//                    queryMap.put("count", "1");
//                    queryMap.put("page", curPage);
//                    int type = mCurIndex + 1;//页面序数0-2，而大淘客分页1-3（今日新品，超级人气榜，特卖精选），故+1;
//                    queryMap.put("type", type);
//                },
//                portItem -> {
//                    int TOTAL_COUNTER = portItem.getTotal_num();
//                    tv_total.setText(String.valueOf(TOTAL_COUNTER));
//                    // 一定要在mRecyclerView.post里面更新数据。
//                    mRecyclerView.post(() -> {
//                        // 如果有下一页则调用addData，不需要把下一页数据add到list里面，直接新的数据给adapter即可。
//                        mQuickAdapter.addData(portItem.getResult());
//                        mCurrentCounter = mQuickAdapter.getData().size();
//                        if (mCurrentCounter >= TOTAL_COUNTER) {
//                            // 数据全部加载完毕就调用 loadComplete
//                            mQuickAdapter.loadComplete();
//                            if (notLoadingView == null) {
//                                notLoadingView = getActivity().getLayoutInflater().inflate(R.layout.include_index_not_loading, (ViewGroup) mRecyclerView.getParent(), false);
//                                notLoadingView.setOnClickListener(v -> CommonUtils.joinQQGroup(getActivity()));
//                            }
//                            mQuickAdapter.addFooterView(notLoadingView);
//                        } else {
//                            curPage++;
//                        }
//                    });
//                },
//                throwable -> {
//                    mQuickAdapter.showLoadMoreFailedView();
//                    Logger.e(throwable.getMessage());
//                });
    }

    private void setUpViews(View parentView) {
        rl_no_data = parentView.findViewById(R.id.rl_no_data);
        rl_no_network = parentView.findViewById(R.id.rl_no_network);
        mRecyclerView = parentView.findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = parentView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red,
                R.color.orange, R.color.green,
                R.color.blue);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = parentView.findViewById(R.id.fab);
        fab.setOnClickListener(v -> mRecyclerView.scrollToPosition(0));
        rl_top_bar = parentView.findViewById(R.id.rl_top_bar);
        tv_now = parentView.findViewById(R.id.tv_now);
        tv_total = parentView.findViewById(R.id.tv_total);
        mRecyclerView.addOnScrollListener(hidingScrollListener);
        initAdapter();
        // 主动刷新数据
        onRefresh();
    }

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

}
