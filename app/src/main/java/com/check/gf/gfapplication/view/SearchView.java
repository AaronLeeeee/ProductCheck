package com.check.gf.gfapplication.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.adapter.SearchViewAdapter;
import com.check.gf.gfapplication.model.SearchInfo;

import java.util.List;

/**
 * 搜索视图
 * <p>
 * Created by wqd on 2017/12/18.
 */

public class SearchView extends LinearLayout implements SearchFooterView.OnSearchListener {

    private ListView mListView;
    private SearchViewAdapter mSearchAdapter;
    private SearchFooterView mFooterView;

    private Animation mInAnimation;
    private Animation mOutAnimation;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_search, this);
        mListView = (ListView) findViewById(R.id.lv_search);
        mSearchAdapter = new SearchViewAdapter(getContext());
        mListView.setAdapter(mSearchAdapter);
        mFooterView = new SearchFooterView(getContext());
        mFooterView.setOnSearchListener(this);
        mListView.addFooterView(mFooterView);
    }

    public void showSearchView() {
        mInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_bottom);
        setVisibility(VISIBLE);
        startAnimation(mInAnimation);
    }

    public void dismissSearchView() {
        mOutAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_bottom);
        startAnimation(mOutAnimation);
        setVisibility(GONE);
    }

    @Override
    public void onSearch() {
        dismissSearchView();
        List<SearchInfo> searchInfoList = mSearchAdapter.getSearchInfoList();

        if (searchInfoList == null) {
            return;
        }

        for (SearchInfo searchInfo : searchInfoList) {
            if (searchInfo == null || TextUtils.isEmpty(searchInfo.content)) {
                continue;
            }

        }

    }
}
