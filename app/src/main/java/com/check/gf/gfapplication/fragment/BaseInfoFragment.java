package com.check.gf.gfapplication.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.config.AppConfig;
import com.check.gf.gfapplication.model.IncomeCheck;
import com.check.gf.gfapplication.utils.TimeUtils;

/**
 * Created by wqd on 2018/1/3.
 */

public class BaseInfoFragment extends Fragment {

    private static final String INCOME_CHECK = "income_check";
    private TextView mPurchaseIdTv;
    private TextView mSupplierTv;
    private TextView mMaterialIdTv;
    private TextView mMaterialNameTv;
    private TextView mQMNOTv;
    private TextView mIncomeCountTv;
    private TextView mSamplePlanTv;
    private TextView mInspectorTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private TextView mSurfaceTv;
    private TextView mDimensionTv;
    private TextView mPerformanceTv;
    private TextView mStartCheckBt;
    private TextView mSubmitCheckBt;

    private IncomeCheck mIncomeCheck;

    public static BaseInfoFragment newInstance(IncomeCheck incomeCheck) {

        Bundle args = new Bundle();
        args.putSerializable(INCOME_CHECK, incomeCheck);
        BaseInfoFragment fragment = new BaseInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_base_info, container, false);
        mPurchaseIdTv = (TextView) layout.findViewById(R.id.tv_purchase_order_id);
        mSupplierTv = (TextView) layout.findViewById(R.id.tv_supplier);
        mMaterialIdTv = (TextView) layout.findViewById(R.id.tv_material_id);
        mMaterialNameTv = (TextView) layout.findViewById(R.id.tv_material_name);
        mQMNOTv = (TextView) layout.findViewById(R.id.tv_qm_no);
        mIncomeCountTv = (TextView) layout.findViewById(R.id.tv_income_count);
        mSamplePlanTv = (TextView) layout.findViewById(R.id.tv_sample_plan);
        mInspectorTv = (TextView) layout.findViewById(R.id.tv_inspector);
        mStartTimeTv = (TextView) layout.findViewById(R.id.tv_start_time);
        mEndTimeTv = (TextView) layout.findViewById(R.id.tv_end_time);
        mSurfaceTv = (TextView) layout.findViewById(R.id.tv_surface);
        mDimensionTv = (TextView) layout.findViewById(R.id.tv_dimension);
        mPerformanceTv = (TextView) layout.findViewById(R.id.tv_performance);

        mStartCheckBt = (TextView) layout.findViewById(R.id.tv_start_check);
        mSubmitCheckBt = (TextView) layout.findViewById(R.id.tv_submit);

        mStartCheckBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mStartTimeTv.getText() != null && !mStartTimeTv.getText().equals("") ) {
                    Toast.makeText(getActivity(), "已经开始检测，请勿重复检查！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mStartTimeTv.setText(TimeUtils.getAllFormat24Str(System.currentTimeMillis()));
            }
        });

        mSubmitCheckBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        mIncomeCheck = (IncomeCheck) bundle.getSerializable(INCOME_CHECK);
        if (mIncomeCheck == null) {
            return;
        }
        mPurchaseIdTv.setText(mIncomeCheck.purchaseOrderId);
        mSupplierTv.setText(mIncomeCheck.supplier);
        mMaterialIdTv.setText(mIncomeCheck.materialId);
        mMaterialNameTv.setText(mIncomeCheck.materialName);
        mQMNOTv.setText("");
        mIncomeCountTv.setText(String.valueOf(mIncomeCheck.incomeCount));
        mSamplePlanTv.setText("");
        mInspectorTv.setText(AppConfig.mUserName);

        mEndTimeTv.setText("");
        mSurfaceTv.setText("0/4    (NG;0)");
        mDimensionTv.setText("0/8    (NG:0)");
        mPerformanceTv.setText("0/1    (NG:0)");
    }
}
