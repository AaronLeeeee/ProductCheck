package com.check.gf.gfapplication.fragment.itemFragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.check.gf.gfapplication.adapter.PortItemAdapter;
import com.check.gf.gfapplication.base.BaseListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Item 左栏实现页面
 */
public class ItemFragmentLeftInstance extends BaseListFragment {
    private static final String FRAGMENT_INDEX = "fragment_index";
    private PortItemAdapter mQuickAdapter;
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

        }

        @Override
        public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

        }

        @Override
        public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        }
    };

    /**
     * 创建新实例
     */
    public static ItemFragmentLeftInstance newInstance(int index) {
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, index);
        ItemFragmentLeftInstance fragment = new ItemFragmentLeftInstance();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initAdapter() {
        //获得索引值
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCurIndex = bundle.getInt(FRAGMENT_INDEX);
        }
        mQuickAdapter = new PortItemAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mQuickAdapter);
        mRecyclerView.addOnItemTouchListener(SimpleClickListener);
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
        Map<String, Object> queryMap = new HashMap<>();
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

}

