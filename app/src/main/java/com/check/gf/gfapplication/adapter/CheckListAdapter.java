package com.check.gf.gfapplication.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.model.IncomeCheck;
import com.check.gf.gfapplication.view.TitleContentTextView;

/**
 * Created by wqd on 2018/1/1.
 */

public class CheckListAdapter extends BaseAdapter {

    private Context mContext;
    private List<IncomeCheck> mIncomeCheckList;
    private GoDetailClickListener mGoDetailClickListener;

    public CheckListAdapter(Context context) {
        mContext = context;
    }

    public void bindData(List<IncomeCheck> list) {
        mIncomeCheckList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mIncomeCheckList == null ? 0 : mIncomeCheckList.size();
    }

    @Override
    public IncomeCheck getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return mIncomeCheckList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_check_list, null);
            viewHolder.purchaseOrderTv = (TitleContentTextView) convertView.findViewById(R.id.tv_purchase_order);
            viewHolder.materialIdTv = (TitleContentTextView) convertView.findViewById(R.id.tv_material_id);
            viewHolder.materialNameTv = (TitleContentTextView) convertView.findViewById(R.id.tv_material_name);
            viewHolder.incomeCountTv = (TitleContentTextView) convertView.findViewById(R.id.tv_income_count);
            viewHolder.checkDateTv = (TitleContentTextView) convertView.findViewById(R.id.tv_check_date);
            viewHolder.supplierTv = (TitleContentTextView) convertView.findViewById(R.id.tv_supplier);
            viewHolder.goDetailLl = (LinearLayout) convertView.findViewById(R.id.ll_go_detail);
            viewHolder.checkIdTv = (TextView) convertView.findViewById(R.id.tv_check_id);
            viewHolder.completionTv = (TextView) convertView.findViewById(R.id.tv_completion);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        IncomeCheck incomeCheck = getItem(position);
        if (incomeCheck == null) {
            return convertView;
        }
        viewHolder.purchaseOrderTv.setContentTv(incomeCheck.purchaseOrderId);
        viewHolder.materialIdTv.setContentTv(incomeCheck.materialId);
        viewHolder.materialNameTv.setContentTv(incomeCheck.materialName);
        viewHolder.incomeCountTv.setContentTv(String.valueOf(incomeCheck.incomeCount));
        viewHolder.checkDateTv.setContentTv(incomeCheck.checkDate);
        viewHolder.supplierTv.setContentTv(incomeCheck.supplier);
        viewHolder.goDetailLl.setTag(R.id.position, incomeCheck);
        viewHolder.goDetailLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag(R.id.position) instanceof IncomeCheck) {
                    if (mGoDetailClickListener != null) {
                        mGoDetailClickListener.goDetail((IncomeCheck) v.getTag(R.id.position));
                    }
                }
            }
        });
        viewHolder.checkIdTv.setText(mContext.getString(R.string.check_order_id, incomeCheck.checkSingleId));
        String completion = String.valueOf(incomeCheck.totalCheckNum - incomeCheck.needCheckNum) +  " / " + String.valueOf(incomeCheck.totalCheckNum);
        viewHolder.completionTv.setText(mContext.getString(R.string.completion, completion));
        return convertView;
    }

    private static class ViewHolder {
        TitleContentTextView purchaseOrderTv;
        TitleContentTextView materialIdTv;
        TitleContentTextView materialNameTv;
        TitleContentTextView incomeCountTv;
        TitleContentTextView checkDateTv;
        TitleContentTextView supplierTv;
        LinearLayout goDetailLl;
        TextView checkIdTv;
        TextView completionTv;
    }

    public void setGoDetailClickListener(GoDetailClickListener listener) {
        mGoDetailClickListener = listener;
    }

    public interface GoDetailClickListener {
        void goDetail(IncomeCheck incomeCheck);
    }
}
