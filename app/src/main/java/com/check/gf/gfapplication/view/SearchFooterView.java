package com.check.gf.gfapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.check.gf.gfapplication.R;

/**
 * Created by wqd on 2018/1/1.
 */

public class SearchFooterView extends LinearLayout {

    private Context mContext;
    private OnSearchListener mOnSearchListener;

    public SearchFooterView(Context context) {
        this(context, null);
    }

    public SearchFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_search_footer, this);
        findViewById(R.id.bt_search).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSearchListener != null) {
                    mOnSearchListener.onSearch();
                }
            }
        });
    }

    public void setOnSearchListener(OnSearchListener listener) {
        mOnSearchListener = listener;
    }

    public interface OnSearchListener {
        void onSearch();
    }


}
