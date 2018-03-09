package com.check.gf.gfapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.check.gf.gfapplication.R;

/**
 * Created by wqd on 2018/1/15.
 */

public class HeaderView extends LinearLayout {

    private TextView mHeaderTv;

    public HeaderView(Context context) {
        this(context, null);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.view_header, this);
        mHeaderTv = (TextView) findViewById(R.id.tv_title);
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeaderView);
        mHeaderTv.setText(ta.getString(R.styleable.HeaderView_header));
        ta.recycle();

    }

    public void setHeader(String str) {
        mHeaderTv.setText(str);
    }
}
