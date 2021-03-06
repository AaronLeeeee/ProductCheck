package com.check.gf.gfapplication.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.check.gf.gfapplication.CustomApplication;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.activity.CheckDetailActivity;
import com.check.gf.gfapplication.base.BaseFragment;
import com.check.gf.gfapplication.entity.CheckOrderInfo;
import com.check.gf.gfapplication.network.RxFactory;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * 基本信息页
 *
 * @author nEdAy
 */
public class BaseInfoFragment extends BaseFragment {

    private static final String BASE_INFO = "base_info";

    private TextView tv_equipment_no_second;
    private TextView tv_equipment_no;
    private TextView mPurchaseIdTv;
    private TextView mSupplierTv;
    private TextView mMaterialCodeTv;
    private TextView mMaterialIdTv;
    private TextView mMaterialNameTv;
    private TextView mIncomeCountTv;
    private TextView mInspectorTv;
    private TextView mStartTimeTv;
    private TextView mEndTimeTv;
    private TextView mSurfaceTv;
    private TextView mDimensionTv;
    private TextView mPerformanceTv;
    private TextView mStartCheckBt;
    private TextView mEndCheckBt;

    private CheckOrderInfo mCheckOrderInfo;
    private OnTestBeginListener mCallback;

    private String mRealName;

    public static BaseInfoFragment newInstance(CheckOrderInfo checkOrderInfo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BASE_INFO, checkOrderInfo);
        BaseInfoFragment fragment = new BaseInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 这个方法是用来确认当前的Activity容器是否已经继承了该接口，如果没有将抛出异常
        try {
            mCallback = (OnTestBeginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onTestListener");
        }
    }


    @Override
    public int bindLayout() {
        return R.layout.fragment_base_info;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPurchaseIdTv = parentView.findViewById(R.id.tv_purchase_order_id);
        mSupplierTv = parentView.findViewById(R.id.tv_supplier);
        mMaterialCodeTv = parentView.findViewById(R.id.tv_material_code);
        mMaterialIdTv = parentView.findViewById(R.id.tv_material_id);
        mMaterialNameTv = parentView.findViewById(R.id.tv_material_name);
        mIncomeCountTv = parentView.findViewById(R.id.tv_income_count);
        mInspectorTv = parentView.findViewById(R.id.tv_inspector);
        mStartTimeTv = parentView.findViewById(R.id.tv_start_time);
        mEndTimeTv = parentView.findViewById(R.id.tv_end_time);
        mSurfaceTv = parentView.findViewById(R.id.tv_surface);
        mDimensionTv = parentView.findViewById(R.id.tv_dimension);
        mPerformanceTv = parentView.findViewById(R.id.tv_performance);
        mStartCheckBt = parentView.findViewById(R.id.tv_start_check);

        tv_equipment_no_second = parentView.findViewById(R.id.tv_equipment_no_second);
        tv_equipment_no = parentView.findViewById(R.id.tv_equipment_no);
        mRealName = CustomApplication.getInstance().getSpHelper().getRealname();

        mStartCheckBt.setOnClickListener(v -> {
            if (mStartTimeTv.getText() != null && !mStartTimeTv.getText().equals("")) {
                ToastUtils.showShort("已经开始检测，请勿重复检查！");
            } else {
                queryStartCheck(mCheckOrderInfo.getEquipmentNo(), mCheckOrderInfo.getMaterialCode(), mCheckOrderInfo.getEquipmentNoSecond(), mCheckOrderInfo.getDocNo());
            }
        });
        mEndCheckBt = parentView.findViewById(R.id.tv_end_check);
        mEndCheckBt.setOnClickListener(v -> {
            if (mStartTimeTv.getText() != null && mStartTimeTv.getText().equals("")) {
                ToastUtils.showShort("尚未开始检测，请勿结束检查！");
            } else {
                queryFinishCheck(mCheckOrderInfo.getEquipmentNo(),
                        mCheckOrderInfo.getEquipmentNoSecond());
            }
        });
    }

    private void queryStartCheck(String equipmentNo, String materialCode, String equipmentNoSecond, String docNo) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .StartCheck(equipmentNo, materialCode, equipmentNoSecond, mRealName, docNo),
                () -> ToastUtils.showShort("请求开始检测..."),
                checkOrderInfoResult -> {
                    if (checkOrderInfoResult.getResult() == 0) {
                        ToastUtils.showShort("开始检测成功!");
                        String startCheckTime = checkOrderInfoResult.getData().getStartCheckTime();
                        mStartTimeTv.setText(startCheckTime != null ? startCheckTime : "");
                        mCallback.onTestBegin(true);
                        mStartCheckBt.setEnabled(false);
                    } else {
                        startCheckError(checkOrderInfoResult.getDesc());
                    }
                },
                throwable -> startCheckError(throwable.getMessage()));
    }


    private void startCheckError(String desc) {
        ToastUtils.showShort("开始检测失败");
        Logger.e(desc);
    }

    private void queryFinishCheck(String equipmentNo, String equipmentNoSecond) {
        toSubscribe(RxFactory.getCheckServiceInstance()
                        .FinishCheck(equipmentNo, equipmentNoSecond, mRealName),
                () -> ToastUtils.showShort("请求结束检测..."),
                checkOrderInfoResult -> {
                    if (checkOrderInfoResult.getResult() == 0) {
                        ToastUtils.showShort("结束检测成功!");
                        String finishCheckTime = checkOrderInfoResult.getData().getFinishCheckTime();
                        mEndTimeTv.setText(finishCheckTime != null ? finishCheckTime : "");
                        mEndCheckBt.setEnabled(false);
                        CheckDetailActivity.getInstance().setFinishCheck(true);
                    } else {
                        finishCheckError(checkOrderInfoResult.getDesc());
                    }
                },
                throwable -> finishCheckError(throwable.getMessage()));
    }

    private void finishCheckError(String desc) {
        ToastUtils.showShort("结束检测失败");
        Logger.e(desc);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        //获得索引值
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(BASE_INFO)) {
            mCheckOrderInfo = bundle.getParcelable(BASE_INFO);
        }
        if (mCheckOrderInfo != null) {
            tv_equipment_no_second.setText(mCheckOrderInfo.getEquipmentNoSecond());
            tv_equipment_no.setText(mCheckOrderInfo.getEquipmentNo());
            mPurchaseIdTv.setText(mCheckOrderInfo.getCustomerCode());
            mSupplierTv.setText(mCheckOrderInfo.getCustomerName());
            mMaterialCodeTv.setText(mCheckOrderInfo.getMaterialCode());
            mMaterialIdTv.setText(mCheckOrderInfo.getItemCode());
            mMaterialNameTv.setText(mCheckOrderInfo.getItemName());
            //mQMNOTv.setText("");
            mIncomeCountTv.setText(mCheckOrderInfo.getPackgNum());
            //mSamplePlanTv.setText("");
            CustomApplication customApplication = CustomApplication.getInstance();
            String realname = customApplication.getSpHelper().getRealname();
            mInspectorTv.setText(realname);
            String startCheckTime = mCheckOrderInfo.getStartCheckTime();
            if (TextUtils.isEmpty(startCheckTime)) {
                mStartCheckBt.setEnabled(true);
            } else {
                mStartCheckBt.setEnabled(false);
                mCallback.onTestBegin(true);
                mStartTimeTv.setText(mCheckOrderInfo.getStartCheckTime());
            }
            String finishCheckTime = mCheckOrderInfo.getFinishCheckTime();
            if (TextUtils.isEmpty(finishCheckTime)) {
                mEndCheckBt.setEnabled(true);
            } else {
                mEndCheckBt.setEnabled(false);
                mEndTimeTv.setText(finishCheckTime);
                CheckDetailActivity.getInstance().setFinishCheck(true);
            }
            List<CheckOrderInfo.CheckDataBean> checkDataBeans = mCheckOrderInfo.getCheckData();
            if (checkDataBeans != null && checkDataBeans.size() != 0) {
                for (int i = 0; i < checkDataBeans.size(); i++) {
                    CheckOrderInfo.CheckDataBean checkDataBean = checkDataBeans.get(i);
                    if (checkDataBean != null) {
                        String inspectCode = checkDataBean.getInspectCode();
                        String typeName = checkDataBean.getTypeName();
                        int finishCheckNumber = checkDataBean.getFinishCheckNumber();
                        int totalCheckNumber = checkDataBean.getTotalCheckNumber();
                        String text = finishCheckNumber + "/" + totalCheckNumber;
                        if ("LT001".equals(inspectCode) || "外观".equals(typeName)) {
                            mSurfaceTv.setText(text + " (NG;0)");
                        } else if ("LT002".equals(inspectCode) || "尺寸".equals(typeName)) {
                            mDimensionTv.setText(text + " (NG:0)");
                        } else if ("LT003".equals(inspectCode) || "性能".equals(typeName)) {
                            mPerformanceTv.setText(text + " (NG:0)");
                        }
                    }
                }
            }
        } else {
            ToastUtils.showShort("程序异常，请重试");
            getActivityNonNull().finish();
        }

    }

    public interface OnTestBeginListener {
        void onTestBegin(boolean enable);
    }

}
