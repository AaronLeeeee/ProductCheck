package com.check.gf.gfapplication.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.utils.CommonUtils;

import me.shaohui.bottomdialog.BaseBottomDialog;

/**
 * author : nEdAy
 * time   : 2018/3/10
 * desc   :
 */

public class SearchBottomDialog extends BaseBottomDialog {
    //{"客户", "发货日期", "出货计划单号", "设备号", "客户订单号"};

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_layout_search;
    }

    @Override
    public void bindView(View view) {
        EditText et_customerName = view.findViewById(R.id.et_customerName);
        TextView tv_requireDate_text = view.findViewById(R.id.tv_requireDate_text);
        EditText et_equipmentNo = view.findViewById(R.id.et_equipmentNo);
        EditText et_docNo = view.findViewById(R.id.et_docNo);
        EditText et_custNo = view.findViewById(R.id.et_custNo);
        view.findViewById(R.id.btn_search).setOnClickListener(view1 -> {
            String customerName = et_customerName.getText().toString().trim();
            String requireDate_text = tv_requireDate_text.getText().toString().trim();
            String equipmentNo = et_equipmentNo.getText().toString().trim();
            String docNo = et_docNo.getText().toString().trim();
            String custNo = et_custNo.getText().toString().trim();
            if (TextUtils.isEmpty(customerName) && TextUtils.isEmpty(requireDate_text) &&
                    TextUtils.isEmpty(equipmentNo) && TextUtils.isEmpty(docNo)
                    && TextUtils.isEmpty(custNo)) {
                CommonUtils.showToast("条件不允许同时为空");
            } else {

            }
        });

    }

    @Override
    public int getHeight() { // 设置 bottomDialog 的高度
        return super.getHeight();
    }

    @Override
    public float getDimAmount() { // 设置 dialog 所在 window 的背景深度，默认0.2f
        return super.getDimAmount();
    }

    @Override
    public boolean getCancelOutside() { // 设置 dialog 点击外部区域是否消失
        return super.getCancelOutside();
    }

    @Override
    public String getFragmentTag() { // 设置 dialogFragment 的 tag
        return super.getFragmentTag();
    }

}
