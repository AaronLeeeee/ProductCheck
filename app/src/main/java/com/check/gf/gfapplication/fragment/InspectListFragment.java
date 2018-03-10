package com.check.gf.gfapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.InspectListAdapter;
import com.check.gf.gfapplication.base.BaseFragment;
import com.check.gf.gfapplication.base.IBaseList;
import com.check.gf.gfapplication.entity.InspectItem;
import com.check.gf.gfapplication.network.RxFactory;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 尺寸
 *
 * @author nEdAy
 */
public class InspectListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IBaseList {

    private static final String INSPECT_CODE = "inspect_code";
    private static final String EQUIPMENT_NO = "equipment_no";

    private String mInspectCode;
    private String mEquipmentNo;

    private List<InspectItem.DataBean> inspectItems;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout rl_no_data, rl_no_network;
    private InspectListAdapter mQuickAdapter;

    public static InspectListFragment newInstance(String inspectCode, String equipmentNo) {
        Bundle bundle = new Bundle();
        InspectListFragment fragment = new InspectListFragment();
        bundle.putString(INSPECT_CODE, inspectCode);
        bundle.putString(EQUIPMENT_NO, equipmentNo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_inspect_list, container, false);
        setUpViews(layout);
        return layout;
    }

    public void initAdapter() {
        //获得索引值
        Bundle bundle = getArguments();
        if (bundle != null) {
            mInspectCode = bundle.getString(INSPECT_CODE);
            mEquipmentNo = bundle.getString(EQUIPMENT_NO);
        }
        mQuickAdapter = new InspectListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mQuickAdapter);
        // 一行代码搞定（默认为渐显效果）
        mQuickAdapter.openLoadAnimation();
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            // inspectItems.get(position);
        });
    }

    /**
     * 当SwipeRefreshLayout下拉刷新时触发
     */
    public void RefreshItem() {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .InspectItemListQuery(mInspectCode, mEquipmentNo),
                () -> {
                    // 隐藏无网络和无数据界面
                    rl_no_network.setVisibility(View.GONE);
                    rl_no_data.setVisibility(View.GONE);
                    showLoading("查询检验条目...");
                },
                inspectItemResult -> {
                    if (inspectItemResult.getResult() == 0) {
                        hideLoading();
                        inspectItems = inspectItemResult.getData();
                        if (inspectItems.isEmpty()) {
                            rl_no_data.setVisibility(View.VISIBLE);
                            rl_no_data.setOnClickListener(v -> RefreshItem());
                        }
                        mQuickAdapter.setNewData(inspectItems);
                        mQuickAdapter.removeAllFooterView();
                        mSwipeRefreshLayout.setRefreshing(false);
                    } else {
                        inspectItemListQueryError(inspectItemResult.getDesc());
                    }
                },
                throwable -> inspectItemListQueryError(throwable.getMessage()));
    }

    private void inspectItemListQueryError(String message) {
        hideLoading();
        rl_no_network.setVisibility(View.VISIBLE);
        rl_no_network.setOnClickListener(v -> RefreshItem());
        mQuickAdapter.getData().clear();
        mQuickAdapter.removeAllFooterView();
        mSwipeRefreshLayout.setRefreshing(false);
        Logger.e(message);
    }

    /**
     * 当mRecyclerView上拉加载时触发
     */
    public void LoadMoreItem() {

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


}