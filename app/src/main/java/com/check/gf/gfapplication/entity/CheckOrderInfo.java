package com.check.gf.gfapplication.entity;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 检验单详情
 */
public class CheckOrderInfo extends ResultObject {

    /**
     * data : {"id":1,"docNo":"0001","equipmentNo":"0001","itemCode":"0001","itemName":"导轨总成","PlanQtyTU":1,"PackgNum":2,"requireDate":"2018 - 03 - 09","customerName":"测试客户1","customerCode":"0001","totalCheckNum":10,"finishCheckNum":0,"finishState":0,"checkData":[{"inspectCode":"001","typeName":"外观","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"尺寸","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"性能","totalCheckNumber":10,"finishCheckNumber":4}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * docNo : 0001
         * equipmentNo : 0001
         * itemCode : 0001
         * itemName : 导轨总成
         * PlanQtyTU : 1
         * PackgNum : 2
         * requireDate : 2018 - 03 - 09
         * customerName : 测试客户1
         * customerCode : 0001
         * totalCheckNum : 10
         * finishCheckNum : 0
         * finishState : 0
         * checkData : [{"inspectCode":"001","typeName":"外观","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"尺寸","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"性能","totalCheckNumber":10,"finishCheckNumber":4}]
         */

        private int id;
        private String docNo;
        private String equipmentNo;
        private String itemCode;
        private String itemName;
        private int PlanQtyTU;
        private int PackgNum;
        private String requireDate;
        private String customerName;
        private String customerCode;
        private int totalCheckNum;
        private int finishCheckNum;
        private int finishState;
        private List<CheckDataBean> checkData;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDocNo() {
            return docNo;
        }

        public void setDocNo(String docNo) {
            this.docNo = docNo;
        }

        public String getEquipmentNo() {
            return equipmentNo;
        }

        public void setEquipmentNo(String equipmentNo) {
            this.equipmentNo = equipmentNo;
        }

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

        public int getPlanQtyTU() {
            return PlanQtyTU;
        }

        public void setPlanQtyTU(int PlanQtyTU) {
            this.PlanQtyTU = PlanQtyTU;
        }

        public int getPackgNum() {
            return PackgNum;
        }

        public void setPackgNum(int PackgNum) {
            this.PackgNum = PackgNum;
        }

        public String getRequireDate() {
            return requireDate;
        }

        public void setRequireDate(String requireDate) {
            this.requireDate = requireDate;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerCode() {
            return customerCode;
        }

        public void setCustomerCode(String customerCode) {
            this.customerCode = customerCode;
        }

        public int getTotalCheckNum() {
            return totalCheckNum;
        }

        public void setTotalCheckNum(int totalCheckNum) {
            this.totalCheckNum = totalCheckNum;
        }

        public int getFinishCheckNum() {
            return finishCheckNum;
        }

        public void setFinishCheckNum(int finishCheckNum) {
            this.finishCheckNum = finishCheckNum;
        }

        public int getFinishState() {
            return finishState;
        }

        public void setFinishState(int finishState) {
            this.finishState = finishState;
        }

        public List<CheckDataBean> getCheckData() {
            return checkData;
        }

        public void setCheckData(List<CheckDataBean> checkData) {
            this.checkData = checkData;
        }

        public static class CheckDataBean {
            /**
             * inspectCode : 001
             * typeName : 外观
             * totalCheckNumber : 10
             * finishCheckNumber : 4
             */

            private String inspectCode;
            private String typeName;
            private int totalCheckNumber;
            private int finishCheckNumber;

            public String getInspectCode() {
                return inspectCode;
            }

            public void setInspectCode(String inspectCode) {
                this.inspectCode = inspectCode;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public int getTotalCheckNumber() {
                return totalCheckNumber;
            }

            public void setTotalCheckNumber(int totalCheckNumber) {
                this.totalCheckNumber = totalCheckNumber;
            }

            public int getFinishCheckNumber() {
                return finishCheckNumber;
            }

            public void setFinishCheckNumber(int finishCheckNumber) {
                this.finishCheckNumber = finishCheckNumber;
            }
        }
    }
}