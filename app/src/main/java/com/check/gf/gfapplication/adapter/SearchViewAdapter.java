package com.check.gf.gfapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.model.SearchInfo;

/**
 * 搜索视图 适配器
 *
 * Created by wqd on 2018/1/1.
 */

public class SearchViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<SearchInfo> mSearchInfoList;

    public SearchViewAdapter(Context context) {
        mContext = context;
        initSearchInfoList();
    }

    private void initSearchInfoList() {
        mSearchInfoList = new ArrayList<>(5);
        String[] searchArr = {"客户", "发货日期", "出货计划单号", "设备号", "客户订单号"};
        String[] searchKey = {"supplier", "sendData", "planId", "deviceId", "orderId"};
        for (int i = 0; i < searchArr.length; i++) {
            SearchInfo customerInfo = new SearchInfo();
            customerInfo.title = searchArr[i];
            customerInfo.searchKey = searchKey[i];
            mSearchInfoList.add(customerInfo);
        }
    }

    public void bindData(List<SearchInfo> list) {
        mSearchInfoList = list;
        notifyDataSetChanged();
    }

    public List<SearchInfo> getSearchInfoList() {
        return mSearchInfoList;
    }

    @Override
    public int getCount() {
        return mSearchInfoList == null ? 0 : mSearchInfoList.size();
    }

    @Override
    public SearchInfo getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return mSearchInfoList.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_search_info, null);
            viewHolder.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.contentEt = (EditText) convertView.findViewById(R.id.et_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SearchInfo searchInfo = getItem(position);
        viewHolder.titleTv.setText(searchInfo.title);
        viewHolder.contentEt.setText(searchInfo.content);
        viewHolder.contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchInfo.content = s.toString();
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView titleTv;
        EditText contentEt;
    }
}
