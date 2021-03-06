package com.check.gf.gfapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.check.gf.gfapplication.CustomApplication;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.activity.CheckDetailActivity;
import com.check.gf.gfapplication.activity.CheckDetailItemActivity;
import com.check.gf.gfapplication.adapter.InspectListAdapter;
import com.check.gf.gfapplication.base.BaseFragment;
import com.check.gf.gfapplication.base.IBaseList;
import com.check.gf.gfapplication.entity.InspectItem;
import com.check.gf.gfapplication.entity.InspectItemDetail;
import com.check.gf.gfapplication.network.RxFactory;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 尺寸/../..
 *
 * @author nEdAy
 */
public class InspectListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IBaseList {

    private static final String INSPECT_CODE = "inspect_code";
    private static final String EQUIPMENT_NO = "equipment_no";
    private static final String MATERIAL_CODE = "material_code";
    private static final String EQUIPMENT_NO_SECOND = "equipment_no_second";
    private static final String IS_FINISH_CHECK = "is_finish_check";

    private String mInspectCode;
    private String mEquipmentNo;

    private List<InspectItem> inspectItems;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout rl_no_data, rl_no_network;
    private InspectListAdapter mQuickAdapter;

    private String mEquipmentNoSecond;
    private String mRealName;

    public static InspectListFragment newInstance(String inspectCode, String equipmentNo, String equipmentNoSecond) {
        Bundle bundle = new Bundle();
        InspectListFragment fragment = new InspectListFragment();
        bundle.putString(INSPECT_CODE, inspectCode);
        bundle.putString(EQUIPMENT_NO, equipmentNo);
        bundle.putString(EQUIPMENT_NO_SECOND, equipmentNoSecond);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }


    public static String getExtra() {
        return "inspectItemDetail";
    }

    public static String getInspectCodeExtra() {
        return INSPECT_CODE;
    }

    public static String getEquipmentNoExtra() {
        return EQUIPMENT_NO;
    }

    public static String getMaterialCode() {
        return MATERIAL_CODE;
    }

    public static String getEquipmentNoSecond() {
        return EQUIPMENT_NO_SECOND;
    }

    public static String getIsFinishCheck() {
        return IS_FINISH_CHECK;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_inspect_list;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRealName = CustomApplication.getInstance().getSpHelper().getRealname();

        mLoadingView = parentView.findViewById(R.id.loadView);
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
    }

    public void initAdapter() {
        //获得索引值
        Bundle bundle = getArguments();
        if (bundle != null) {
            mInspectCode = bundle.getString(INSPECT_CODE);
            mEquipmentNo = bundle.getString(EQUIPMENT_NO);
            mEquipmentNoSecond = bundle.getString(EQUIPMENT_NO_SECOND);
        }
        mQuickAdapter = new InspectListAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mQuickAdapter);
        // 一行代码搞定（默认为渐显效果）
        mQuickAdapter.openLoadAnimation();
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        // mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
            String itemCode = inspectItems.get(position).getItemCode();
            String materialCode = inspectItems.get(position).getMaterialCode();
            queryInspectItemDetail(itemCode, materialCode);
        });
        addHeadView();
    }

    /**
     * 添加头部
     */
    private void addHeadView() {
        View headView = getActivityNonNull().getLayoutInflater().inflate(R.layout.include_check_list_header,
                (ViewGroup) mRecyclerView.getParent(), false);
        mQuickAdapter.addHeaderView(headView);
    }

    private void queryInspectItemDetail(String itemCode, String materialCode) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .ItemDetailQuery(mInspectCode, mEquipmentNo, materialCode, itemCode, mRealName, mEquipmentNoSecond),
                () -> showLoading("查询详细信息中..."),
                inspectItemDetailResult -> {
                    if (inspectItemDetailResult.getResult() == 0) {
                        hideLoading();
                        InspectItemDetail inspectItemDetail
                                = inspectItemDetailResult.getData();
                        Intent intent = new Intent(getActivity(), CheckDetailItemActivity.class);
                        intent.putExtra(InspectListFragment.getExtra(), inspectItemDetail);
                        intent.putExtra(getInspectCodeExtra(), mInspectCode);
                        intent.putExtra(getEquipmentNoExtra(), mEquipmentNo);
                        intent.putExtra(getMaterialCode(), materialCode);
                        intent.putExtra(getEquipmentNoSecond(), mEquipmentNoSecond);
                        intent.putExtra(getIsFinishCheck(),
                                CheckDetailActivity.getInstance().isFinishCheck());
                        startActivity(intent);
                    } else {
                        queryInspectItemDetailError(inspectItemDetailResult.getDesc());
                    }
                },
                throwable -> queryInspectItemDetailError(throwable.getMessage()));
    }

    private void queryInspectItemDetailError(String msg) {
        hideLoading();
        ToastUtils.showShort("检验条目详细信息查询失败，请重试：" + msg);
        Logger.e(msg);
    }


    /**
     * 当SwipeRefreshLayout下拉刷新时触发
     */
    public void RefreshItem() {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .InspectItemListQuery(mInspectCode, mEquipmentNo, mEquipmentNoSecond),
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
        // rl_no_network.setOnClickListener(v -> RefreshItem());
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
