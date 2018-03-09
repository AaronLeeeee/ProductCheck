package com.check.gf.gfapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.check.gf.gfapplication.R;

/**
 * Created by wqd on 2018/1/1.
 */

public class TitleContentTextView extends LinearLayout {

    private TextView mTitleTv;
    private TextView mContentTv;

    public TitleContentTextView(Context context) {
        this(context, null);
    }

    public TitleContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleContentTextView);
        mTitleTv.setText(ta.getString(R.styleable.TitleContentTextView_title));
        mTitleTv.setTextSize(ta.getDimensionPixelSize(R.styleable.TitleContentTextView_titleSize, 14));
        mTitleTv.setTextColor(ta.getColor(R.styleable.TitleContentTextView_titleColor, context.getResources().getColor(R.color.white)));
        mContentTv.setText(ta.getString(R.styleable.TitleContentTextView_content));
        mContentTv.setTextSize(ta.getDimensionPixelSize(R.styleable.TitleContentTextView_contentSize, 14));
        mContentTv.setTextColor(ta.getColor(R.styleable.TitleContentTextView_contentColor, context.getResources().getColor(R.color.white)));
        ta.recycle();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_title_content_view, this);
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mContentTv = (TextView) findViewById(R.id.tv_content);
    }

    public void setContentTv(String str) {
        mContentTv.setText(str);
    }
}
