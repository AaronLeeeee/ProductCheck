package com.check.gf.gfapplication.adapter;

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
public class CheckListAdapter extends BaseQuickAdapter<CheckOrder.DataBean, BaseViewHolder> {

    public CheckListAdapter(List<CheckOrder.DataBean> items) {
        super(R.layout.list_item_check_list, items);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckOrder.DataBean incomeCheck) {
        helper.setText(R.id.tv_purchase_order,
                mContext.getString(R.string.purchase_order_id, incomeCheck.getCustomerCode()))
                .setText(R.id.tv_material_id,
                        mContext.getString(R.string.material_id_append, incomeCheck.getItemCode()))
                .setText(R.id.tv_material_name,
                        mContext.getString(R.string.material_name_append, incomeCheck.getItemName()))
                .setText(R.id.tv_income_count,
                        mContext.getString(R.string.income_material_count_append, String.valueOf(incomeCheck.getPlanQtyTU())))
                .setText(R.id.tv_check_date,
                        mContext.getString(R.string.check_date, incomeCheck.getRequireDate()))
                .setText(R.id.tv_materialCode,
                        mContext.getString(R.string.material_code, incomeCheck.getMaterialCode()))
                .setText(R.id.tv_check_id,
                        mContext.getString(R.string.check_order_id, incomeCheck.getEquipmentNo()))
                .setText(R.id.tv_completion,
                        mContext.getString(R.string.completion, String.valueOf(incomeCheck.getFinishCheckNum()) + " / " + String.valueOf(incomeCheck.getTotalCheckNum())))
                .addOnClickListener(R.id.ll_go_detail);
    }

}
