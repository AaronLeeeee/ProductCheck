package com.check.gf.gfapplication.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.entity.CheckOrder;

import java.util.List;

/**
 * CheckListAdapter
 *
 * @author nEdAy
 */
public class CheckListAdapter extends BaseQuickAdapter<CheckOrder, BaseViewHolder> {

    public CheckListAdapter(List<CheckOrder> items) {
        super(R.layout.list_item_check_list, items);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOrder checkOrder) {
        helper.setText(R.id.tv_purchase_order,
                mContext.getString(R.string.purchase_order_id, checkOrder.getCustomerCode()))
                .setText(R.id.tv_material_id,
                        mContext.getString(R.string.material_id_append, checkOrder.getItemCode()))
                .setText(R.id.tv_material_name,
                        mContext.getString(R.string.material_name_append, checkOrder.getItemName()))
                .setText(R.id.tv_income_count,
                        mContext.getString(R.string.income_material_count_append, checkOrder.getPackgNum()))
                .setText(R.id.tv_check_date,
                        mContext.getString(R.string.check_date, checkOrder.getRequireDate()))
                .setText(R.id.tv_materialCode,
                        mContext.getString(R.string.material_code, checkOrder.getMaterialCode()))
                .setText(R.id.tv_check_id,
                        mContext.getString(R.string.check_order_id, TextUtils.isEmpty(checkOrder.getEquipmentNoSecond()) ? checkOrder.getEquipmentNo() : checkOrder.getEquipmentNoSecond()))
                .setText(R.id.tv_completion,
                        mContext.getString(R.string.completion, String.valueOf(checkOrder.getFinishCheckNum()) + " / " + String.valueOf(checkOrder.getTotalCheckNum())))
                .addOnClickListener(R.id.ll_go_detail);
    }

}
