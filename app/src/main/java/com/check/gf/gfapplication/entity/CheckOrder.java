package com.check.gf.gfapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 检验单
 */
public class CheckOrder implements Parcelable {
    /**
     * id : 1
     * docNo : 0001
     * equipmentNo : 0001
     * materialCode : 0001
     * itemCode : 0001
     * itemName : 导轨总成
     * planQtyTU : 1
     * packgNum : 2
     * requireDate : 2018 - 03 - 09
     * customerName : 测试客户1
     * customerCode : 0001
     * totalCheckNum : 10
     * finishCheckNum : 0
     * finishState : 0
     */

    private String id;
    private String docNo;
    private String equipmentNo;
    private String equipmentNoSecond;
    private String materialCode;
    private String itemCode;
    private String itemName;
    private int planQtyTU;
    private String packgNum;
    private String requireDate;
    private String customerName;
    private String customerCode;
    private int totalCheckNum;
    private int finishCheckNum;
    private int finishState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return planQtyTU;
    }

    public void setPlanQtyTU(int PlanQtyTU) {
        this.planQtyTU = PlanQtyTU;
    }

    public String getPackgNum() {
        return packgNum;
    }

    public void setPackgNum(String PackgNum) {
        this.packgNum = PackgNum;
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

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public CheckOrder() {
    }

    public String getEquipmentNoSecond() {
        return equipmentNoSecond;
    }

    public void setEquipmentNoSecond(String equipmentNoSecond) {
        this.equipmentNoSecond = equipmentNoSecond;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.docNo);
        dest.writeString(this.equipmentNo);
        dest.writeString(this.equipmentNoSecond);
        dest.writeString(this.materialCode);
        dest.writeString(this.itemCode);
        dest.writeString(this.itemName);
        dest.writeInt(this.planQtyTU);
        dest.writeString(this.packgNum);
        dest.writeString(this.requireDate);
        dest.writeString(this.customerName);
        dest.writeString(this.customerCode);
        dest.writeInt(this.totalCheckNum);
        dest.writeInt(this.finishCheckNum);
        dest.writeInt(this.finishState);
    }

    protected CheckOrder(Parcel in) {
        this.id = in.readString();
        this.docNo = in.readString();
        this.equipmentNo = in.readString();
        this.equipmentNoSecond = in.readString();
        this.materialCode = in.readString();
        this.itemCode = in.readString();
        this.itemName = in.readString();
        this.planQtyTU = in.readInt();
        this.packgNum = in.readString();
        this.requireDate = in.readString();
        this.customerName = in.readString();
        this.customerCode = in.readString();
        this.totalCheckNum = in.readInt();
        this.finishCheckNum = in.readInt();
        this.finishState = in.readInt();
    }

    public static final Creator<CheckOrder> CREATOR = new Creator<CheckOrder>() {
        @Override
        public CheckOrder createFromParcel(Parcel source) {
            return new CheckOrder(source);
        }

        @Override
        public CheckOrder[] newArray(int size) {
            return new CheckOrder[size];
        }
    };
}

