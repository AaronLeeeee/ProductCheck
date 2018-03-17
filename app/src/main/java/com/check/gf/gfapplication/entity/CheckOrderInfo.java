package com.check.gf.gfapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 检验单详情
 */
public class CheckOrderInfo extends ResultObject implements Parcelable {

    /**
     * data : {"id":1,"docNo":"0001","equipmentNo":"0001","itemCode":"0001","itemName":"导轨总成","PlanQtyTU":1,"PackgNum":2,"requireDate":"2018 - 03 - 09","customerName":"测试客户1","customerCode":"0001","totalCheckNum":10,"finishCheckNum":0,"startCheckTime":"2018-03-09 11:30","finishCheckTime":"2018-03-09 12:30","finishState":0,"checkData":[{"inspectCode":"001","typeName":"外观","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"尺寸","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"性能","totalCheckNumber":10,"finishCheckNumber":4}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 1
         * docNo : 0001
         * equipmentNo : 0001
         * materialCode : 0001
         * itemCode : 0001
         * itemName : 导轨总成
         * PlanQtyTU : 1
         * PackgNum : 2
         * requireDate : 2018 - 03 - 09
         * customerName : 测试客户1
         * customerCode : 0001
         * totalCheckNum : 10
         * finishCheckNum : 0
         * startCheckTime : 2018-03-09 11:30
         * finishCheckTime : 2018-03-09 12:30
         * finishState : 0
         * checkData : [{"inspectCode":"001","typeName":"外观","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"尺寸","totalCheckNumber":10,"finishCheckNumber":4},{"inspectCode":"002","typeName":"性能","totalCheckNumber":10,"finishCheckNumber":4}]
         */

        private int id;
        private String docNo;
        private String equipmentNo;
        private String materialCode;
        private String itemCode;
        private String itemName;
        private int PlanQtyTU;
        private int PackgNum;
        private String requireDate;
        private String customerName;
        private String customerCode;
        private int totalCheckNum;
        private int finishCheckNum;
        private String startCheckTime;
        private String finishCheckTime;
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

        public String getStartCheckTime() {
            return startCheckTime;
        }

        public void setStartCheckTime(String startCheckTime) {
            this.startCheckTime = startCheckTime;
        }

        public String getFinishCheckTime() {
            return finishCheckTime;
        }

        public void setFinishCheckTime(String finishCheckTime) {
            this.finishCheckTime = finishCheckTime;
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

        public String getMaterialCode() {
            return materialCode;
        }

        public void setMaterialCode(String materialCode) {
            this.materialCode = materialCode;
        }

        public static class CheckDataBean implements Parcelable {
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.inspectCode);
                dest.writeString(this.typeName);
                dest.writeInt(this.totalCheckNumber);
                dest.writeInt(this.finishCheckNumber);
            }

            public CheckDataBean() {
            }

            protected CheckDataBean(Parcel in) {
                this.inspectCode = in.readString();
                this.typeName = in.readString();
                this.totalCheckNumber = in.readInt();
                this.finishCheckNumber = in.readInt();
            }

            public static final Creator<CheckDataBean> CREATOR = new Creator<CheckDataBean>() {
                @Override
                public CheckDataBean createFromParcel(Parcel source) {
                    return new CheckDataBean(source);
                }

                @Override
                public CheckDataBean[] newArray(int size) {
                    return new CheckDataBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.docNo);
            dest.writeString(this.equipmentNo);
            dest.writeString(this.materialCode);
            dest.writeString(this.itemCode);
            dest.writeString(this.itemName);
            dest.writeInt(this.PlanQtyTU);
            dest.writeInt(this.PackgNum);
            dest.writeString(this.requireDate);
            dest.writeString(this.customerName);
            dest.writeString(this.customerCode);
            dest.writeInt(this.totalCheckNum);
            dest.writeInt(this.finishCheckNum);
            dest.writeString(this.startCheckTime);
            dest.writeString(this.finishCheckTime);
            dest.writeInt(this.finishState);
            dest.writeTypedList(this.checkData);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.docNo = in.readString();
            this.equipmentNo = in.readString();
            this.materialCode = in.readString();
            this.itemCode = in.readString();
            this.itemName = in.readString();
            this.PlanQtyTU = in.readInt();
            this.PackgNum = in.readInt();
            this.requireDate = in.readString();
            this.customerName = in.readString();
            this.customerCode = in.readString();
            this.totalCheckNum = in.readInt();
            this.finishCheckNum = in.readInt();
            this.startCheckTime = in.readString();
            this.finishCheckTime = in.readString();
            this.finishState = in.readInt();
            this.checkData = in.createTypedArrayList(CheckDataBean.CREATOR);
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public CheckOrderInfo() {
    }

    protected CheckOrderInfo(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CheckOrderInfo> CREATOR = new Parcelable.Creator<CheckOrderInfo>() {
        @Override
        public CheckOrderInfo createFromParcel(Parcel source) {
            return new CheckOrderInfo(source);
        }

        @Override
        public CheckOrderInfo[] newArray(int size) {
            return new CheckOrderInfo[size];
        }
    };
}