package com.check.gf.gfapplication.entity;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 检验条目信息
 */
public class InspectItem extends ResultObject {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * itemCode : 001
         * itemName : 表面不能有破损，摩察系数不能太大
         * checkResult : 0
         * checkContent : 检验结果备注
         */

        private String itemCode;
        private String itemName;
        private int checkResult;
        private String checkContent;

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(int checkResult) {
            this.checkResult = checkResult;
        }

        public String getCheckContent() {
            return checkContent;
        }

        public void setCheckContent(String checkContent) {
            this.checkContent = checkContent;
        }
    }
}