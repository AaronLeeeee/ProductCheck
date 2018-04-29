package com.check.gf.gfapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * time   : 2018/3/13
 * desc   :
 */

public class SearchItem implements Parcelable {
    private String customerName;
    private String mRequireDate;
    private String equipmentNo;
    private String docNo;
    private String materialCode;
    private String custNo;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getmRequireDate() {
        return mRequireDate;
    }

    public void setmRequireDate(String mRequireDate) {
        this.mRequireDate = mRequireDate;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customerName);
        dest.writeString(this.mRequireDate);
        dest.writeString(this.equipmentNo);
        dest.writeString(this.docNo);
        dest.writeString(this.materialCode);
        dest.writeString(this.custNo);
    }

    public SearchItem() {
    }

    protected SearchItem(Parcel in) {
        this.customerName = in.readString();
        this.mRequireDate = in.readString();
        this.equipmentNo = in.readString();
        this.docNo = in.readString();
        this.materialCode = in.readString();
        this.custNo = in.readString();
    }

    public static final Parcelable.Creator<SearchItem> CREATOR = new Parcelable.Creator<SearchItem>() {
        @Override
        public SearchItem createFromParcel(Parcel source) {
            return new SearchItem(source);
        }

        @Override
        public SearchItem[] newArray(int size) {
            return new SearchItem[size];
        }
    };
}
