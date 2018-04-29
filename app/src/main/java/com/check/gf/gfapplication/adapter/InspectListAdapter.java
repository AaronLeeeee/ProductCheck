package com.check.gf.gfapplication.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.entity.InspectItem;

import java.util.List;

/**
 * CheckListAdapter
 *
 * @author nEdAy
 */
public class InspectListAdapter extends BaseQuickAdapter<InspectItem, BaseViewHolder> {

    public InspectListAdapter(List<InspectItem> items) {
        super(R.layout.list_item_check_result, items);
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectItem inspectItem) {
        int checkResult = inspectItem.getCheckResult();
        helper.setText(R.id.tv_num_id, inspectItem.getItemCode())
                .setText(R.id.tv_material_code, inspectItem.getMaterialCode())
                .setText(R.id.tv_num_des, inspectItem.getItemName())
                .setVisible(R.id.iv_checked, checkResult != 0)
                .setImageResource(R.id.iv_checked, checkResult == 1 ? R.drawable.ic_check : R.drawable.ic_uncheck);
    }

}
