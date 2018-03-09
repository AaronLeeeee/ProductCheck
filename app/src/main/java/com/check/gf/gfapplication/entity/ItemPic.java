package com.check.gf.gfapplication.entity;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 检验条目图片信息
 */
public class ItemPic extends ResultObject {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}