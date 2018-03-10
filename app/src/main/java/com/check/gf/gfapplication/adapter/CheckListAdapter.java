package com.check.gf.gfapplication.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.model.IncomeCheck;

import java.util.List;

/**
 * Created by wqd on 2018/1/1.
 */

public class CheckListAdapter extends BaseQuickAdapter<IncomeCheck, BaseViewHolder> {

    public CheckListAdapter(List<IncomeCheck> items) {
        super(R.layout.list_item_check_list, items);
    }

    @Override
    protected void convert(BaseViewHolder helper, IncomeCheck incomeCheck) {
        helper.setText(R.id.tv_purchase_order, incomeCheck.purchaseOrderId)
                .setText(R.id.tv_material_id, incomeCheck.materialId)
                .setText(R.id.tv_material_name, incomeCheck.materialName)
                .setText(R.id.tv_income_count, incomeCheck.incomeCount)
                .setText(R.id.tv_check_date, incomeCheck.checkDate)
                .setText(R.id.tv_supplier, incomeCheck.supplier)
                .setText(R.id.tv_check_id, mContext.getString(R.string.check_order_id, incomeCheck.checkSingleId))
                .setText(R.id.tv_completion, mContext.getString(R.string.completion, String.valueOf(incomeCheck.totalCheckNum - incomeCheck.needCheckNum) + " / " + String.valueOf(incomeCheck.totalCheckNum)))
                .addOnClickListener(R.id.ll_go_detail);
    }

}
