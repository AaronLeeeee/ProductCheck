package com.check.gf.gfapplication.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.check.gf.gfapplication.R;
import com.check.gf.gfapplication.entity.PortItem;

import java.util.List;

/**
 * PortItem 适配器
 *
 * @author nEdAy
 */
public class PortItemAdapter extends BaseQuickAdapter<PortItem, BaseViewHolder> {

    public PortItemAdapter(List<PortItem> items) {
        super(R.layout.layout_basepickerview, items);
    }

    @Override
    protected void convert(BaseViewHolder helper, PortItem item) {
//        helper.setText(R.id.tv_mall_name, item.getIsTmall() == 1 ?
//                mContext.getString(R.string.tx_tianmao) : mContext.getString(R.string.tx_taobao))
//                .setText(R.id.tv_title, item.getTitle())
//                .setText(R.id.tv_money, item.getPrice())
//                .setText(R.id.tv_sales_num, "目前销量：" + item.getSales_num())
//                .setText(R.id.tx_get_value, StringUtils.getPrettyNumber(item.getQuan_price()) + "元券")
//                .setVisible(R.id.lv_text, item.getSales_num() >= 10000)
//                .addOnClickListener(R.id.ll_get)
//                .addOnClickListener(R.id.tx_buy_url);
//
//        String img_url = item.getPic();
//        SimpleDraweeView iv_img = helper.getView(R.id.iv_img_shower);
//        if (img_url != null && !img_url.equals("")) {
//            Uri uri = Uri.parse(img_url);
//            if (CommonUtils.isMobile()) {
//                iv_img.setImageURI(uri + mContext.getString(R.string._200x200_jpg));
//            } else {
//                iv_img.setImageURI(uri + mContext.getString(R.string._300x300_jpg));
//            }
//        } else {
//            iv_img.setImageResource(R.drawable.icon_stub);
//        }
    }


}
