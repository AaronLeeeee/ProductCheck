package com.check.gf.gfapplication.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.check.gf.gfapplication.CustomApplication;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.base.BaseFragment;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.network.RxFactory;
import com.check.gf.gfapplication.utils.CommonUtils;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * 基本信息页
 *
 * @author nEdAy
 */
public class BaseInfoFragment extends BaseFragment {

    private static final String BASE_INFO = "base_info";
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

    private CheckOrderInfo.DataBean checkOrderInfo;
    private onTestListener mCallback;

    public static BaseInfoFragment newInstance(CheckOrderInfo.DataBean checkOrderInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BASE_INFO, checkOrderInfo);
        BaseInfoFragment fragment = new BaseInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
// 这个方法是用来确认当前的Activity容器是否已经继承了该接口，如果没有将抛出异常
        try {
            mCallback = (onTestListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_base_info, container, false);

        mLoadingView = layout.findViewById(R.id.loadView);

        mPurchaseIdTv = layout.findViewById(R.id.tv_purchase_order_id);
        mSupplierTv = layout.findViewById(R.id.tv_supplier);
        mMaterialIdTv = layout.findViewById(R.id.tv_material_id);
        mMaterialNameTv = layout.findViewById(R.id.tv_material_name);
        mQMNOTv = layout.findViewById(R.id.tv_qm_no);
        mIncomeCountTv = layout.findViewById(R.id.tv_income_count);
        mSamplePlanTv = layout.findViewById(R.id.tv_sample_plan);
        mInspectorTv = layout.findViewById(R.id.tv_inspector);
        mStartTimeTv = layout.findViewById(R.id.tv_start_time);
        mEndTimeTv = layout.findViewById(R.id.tv_end_time);
        mSurfaceTv = layout.findViewById(R.id.tv_surface);
        mDimensionTv = layout.findViewById(R.id.tv_dimension);
        mPerformanceTv = layout.findViewById(R.id.tv_performance);

        mStartCheckBt = layout.findViewById(R.id.tv_start_check);
        mSubmitCheckBt = layout.findViewById(R.id.tv_submit);

        mStartCheckBt.setOnClickListener(v -> {
            if (mStartTimeTv.getText() != null && !mStartTimeTv.getText().equals("")) {
                CommonUtils.showToast("已经开始检测，请勿重复检查！");
            } else {
                startCheck(checkOrderInfo.getEquipmentNo());
            }
        });
        mSubmitCheckBt.setOnClickListener(v -> {
            CommonUtils.showToast("暂无该功能！");
        });
        return layout;
    }

    private void startCheck(String equipmentNo) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .StartCheck(equipmentNo),
                () -> CommonUtils.showToast("请求开始检测..."),
                checkOrderInfoResult -> {
                    if (checkOrderInfoResult.getResult() == 0) {
                        CommonUtils.showToast("开始检测成功");
                        String startCheckTime = checkOrderInfoResult.getData().getStartCheckTime();
                        mStartTimeTv.setText(startCheckTime != null ? startCheckTime : "");
                        mCallback.onTest(true);
                        mStartCheckBt.setEnabled(false);
                    } else {
                        startCheckError(checkOrderInfoResult.getDesc());
                    }
                },
                throwable -> startCheckError(throwable.getMessage()));
    }

    private void startCheckError(String desc) {
        CommonUtils.showToast("开始检测失败");
        Logger.e(desc);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        //获得索引值
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(BASE_INFO)) {
            checkOrderInfo = bundle.getParcelable(BASE_INFO);
        }
        if (checkOrderInfo != null) {
            mPurchaseIdTv.setText(checkOrderInfo.getCustomerCode());
            mSupplierTv.setText(checkOrderInfo.getCustomerName());
            mMaterialIdTv.setText(checkOrderInfo.getItemCode());
            mMaterialNameTv.setText(checkOrderInfo.getItemName());
            mQMNOTv.setText("");
            mIncomeCountTv.setText(String.valueOf(checkOrderInfo.getPlanQtyTU()));
            mSamplePlanTv.setText("");
            CustomApplication customApplication = CustomApplication.getInstance();
            String realname = customApplication.getSpHelper().getRealname();
            mInspectorTv.setText(realname);
            String startCheckTime = checkOrderInfo.getStartCheckTime();
            if (TextUtils.isEmpty(startCheckTime)) {
                mStartCheckBt.setEnabled(true);
            } else {
                mStartCheckBt.setEnabled(false);
                mCallback.onTest(true);
                mStartTimeTv.setText(checkOrderInfo.getStartCheckTime());
            }
            mEndTimeTv.setText(checkOrderInfo.getFinishCheckTime());
            List<CheckOrderInfo.DataBean.CheckDataBean> checkDataBeans = checkOrderInfo.getCheckData();
            for (int i = 0; i < checkDataBeans.size(); i++) {
                CheckOrderInfo.DataBean.CheckDataBean checkDataBean = checkDataBeans.get(i);
                String inspectCode = checkDataBean.getInspectCode();
                String typeName = checkDataBean.getTypeName();
                int finishCheckNumber = checkDataBean.getFinishCheckNumber();
                int totalCheckNumber = checkDataBean.getTotalCheckNumber();
                String text = finishCheckNumber + "/" + totalCheckNumber;
                if (inspectCode.equals("001") && typeName.equals("外观")) {
                    mSurfaceTv.setText(text + " (NG;0)");
                } else if (inspectCode.equals("002") && typeName.equals("尺寸")) {
                    mDimensionTv.setText(text + " (NG:0)");
                } else if (inspectCode.equals("003") && typeName.equals("性能")) {
                    mPerformanceTv.setText(text + " (NG:0)");
                }
            }
        } else {
            CommonUtils.showToast("程序异常，请重试");
            getActivity().finish();
        }

    }

    public interface onTestListener {
        void onTest(boolean enable);
    }
}
