package com.check.gf.gfapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.check.gf.gfapplication.R;

/**
 * 尺寸
 * <p>
 * Created by wqd on 2018/1/10.
 */

public class DimensionFragment extends Fragment {

    public static DimensionFragment newInstance() {
        Bundle args = new Bundle();
        DimensionFragment fragment = new DimensionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_dimension, container, false);
        return layout;
    }
}
