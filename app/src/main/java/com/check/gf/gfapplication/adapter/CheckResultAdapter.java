package com.check.gf.gfapplication.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.model.SurfaceInfo;

/**
 * Created by wqd on 2018/2/7.
 */

public class CheckResultAdapter extends BaseAdapter {

    private Context mContext;
    private List<SurfaceInfo> mSurfaceInfoList;

    public CheckResultAdapter(Context context) {
        mContext = context;
    }

    public void bindData(List<SurfaceInfo> list) {
        mSurfaceInfoList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mSurfaceInfoList == null ? 0 : mSurfaceInfoList.size();
    }

    @Override
    public SurfaceInfo getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return mSurfaceInfoList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_check_result, null);
            viewHolder.checkNumTv = (TextView) convertView.findViewById(R.id.tv_num_id);
            viewHolder.checkDesTv = (TextView) convertView.findViewById(R.id.tv_num_des);
            viewHolder.checkIv = (ImageView) convertView.findViewById(R.id.iv_checked);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SurfaceInfo info = getItem(position);
        if (info == null) {
            return convertView;
        }

        viewHolder.checkNumTv.setText(position + 1 + "");
        viewHolder.checkDesTv.setText(info.checkDes);
        viewHolder.checkIv.setVisibility(info.isChecked ?  View.VISIBLE : View.GONE);

        return convertView;
    }

    private static class ViewHolder {
        TextView checkNumTv;
        TextView checkDesTv;
        ImageView checkIv;
    }
}
