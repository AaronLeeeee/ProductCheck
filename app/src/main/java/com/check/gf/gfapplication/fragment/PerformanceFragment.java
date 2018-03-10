package com.check.gf.gfapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.model.IncomeCheck;

/**
 * 性能
 *
 * Created by wqd on 2018/1/10.
 */

public class PerformanceFragment extends Fragment{

    public static PerformanceFragment newInstance(IncomeCheck incomeCheck) {

        Bundle args = new Bundle();

        PerformanceFragment fragment = new PerformanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_performance, container, false);
        return layout;
    }
}
