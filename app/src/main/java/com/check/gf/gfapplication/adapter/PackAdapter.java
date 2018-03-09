package com.check.gf.gfapplication.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.check.gf.gfapplication.R;

/**
 * Created by wqd on 2017/12/13.
 */

public class PackAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;
    private int mCheckPos = 0;

    public PackAdapter(Context context) {
        mContext = context;
    }

    public void bindDate(List<String> list) {
        mData = list;
        notifyDataSetChanged();
    }

    public void setCheckItem(int position) {
        mCheckPos = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public String getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_pack_station, null);
            viewHolder.contentTv = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.dividerView = convertView.findViewById(R.id.v_divider);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.contentTv.setText(getItem(position));
        viewHolder.dividerView.setVisibility(position == getCount() - 1 ? View.GONE : View.VISIBLE);
        return convertView;
    }

    private static class ViewHolder {
        TextView contentTv;
        View dividerView;
    }
}
