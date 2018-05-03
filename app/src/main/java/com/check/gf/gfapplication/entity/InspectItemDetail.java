package com.check.gf.gfapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/11
 * desc   : 检验条目详细信息
 */
public class InspectItemDetail implements Parcelable {

    /**
     * itemCode : 001
     * itemName : 表面不能有破损，摩察系数不能太大
     * checkResult : 0
     * checkContent1 : 检验结果备注1
     * checkContent2 : 检验结果备注2
     * checkContent3 : 检验结果备注3
     * checkContent4 : 检验结果备注4
     * checkContent5 : 检验结果备注5
     * pictures : [{"url":"http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg"},{"url":"http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg"}]
     */

    private String itemCode;
    private String itemName;
    private int checkResult;
    private String checkContent1;
    private String checkContent2;
    private String checkContent3;
    private String checkContent4;
    private String checkContent5;
    private List<PicturesBean> pictures;

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

    public String getCheckContent1() {
        return checkContent1;
    }

    public void setCheckContent1(String checkContent1) {
        this.checkContent1 = checkContent1;
    }

    public String getCheckContent2() {
        return checkContent2;
    }

    public void setCheckContent2(String checkContent2) {
        this.checkContent2 = checkContent2;
    }

    public String getCheckContent3() {
        return checkContent3;
    }

    public void setCheckContent3(String checkContent3) {
        this.checkContent3 = checkContent3;
    }

    public String getCheckContent4() {
        return checkContent4;
    }

    public void setCheckContent4(String checkContent4) {
        this.checkContent4 = checkContent4;
    }

    public String getCheckContent5() {
        return checkContent5;
    }

    public void setCheckContent5(String checkContent5) {
        this.checkContent5 = checkContent5;
    }

    public List<PicturesBean> getPictures() {
        return pictures;
    }

    public void setPictures(List<PicturesBean> pictures) {
        this.pictures = pictures;
    }

    public static class PicturesBean implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.url);
        }

        public PicturesBean() {
        }

        protected PicturesBean(Parcel in) {
            this.url = in.readString();
        }

        public static final Creator<PicturesBean> CREATOR = new Creator<PicturesBean>() {
            @Override
            public PicturesBean createFromParcel(Parcel source) {
                return new PicturesBean(source);
            }

            @Override
            public PicturesBean[] newArray(int size) {
                return new PicturesBean[size];
            }
        };
    }

    public InspectItemDetail() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemCode);
        dest.writeString(this.itemName);
        dest.writeInt(this.checkResult);
        dest.writeString(this.checkContent1);
        dest.writeString(this.checkContent2);
        dest.writeString(this.checkContent3);
        dest.writeString(this.checkContent4);
        dest.writeString(this.checkContent5);
        dest.writeTypedList(this.pictures);
    }

    protected InspectItemDetail(Parcel in) {
        this.itemCode = in.readString();
        this.itemName = in.readString();
        this.checkResult = in.readInt();
        this.checkContent1 = in.readString();
        this.checkContent2 = in.readString();
        this.checkContent3 = in.readString();
        this.checkContent4 = in.readString();
        this.checkContent5 = in.readString();
        this.pictures = in.createTypedArrayList(PicturesBean.CREATOR);
    }

    public static final Creator<InspectItemDetail> CREATOR = new Creator<InspectItemDetail>() {
        @Override
        public InspectItemDetail createFromParcel(Parcel source) {
            return new InspectItemDetail(source);
        }

        @Override
        public InspectItemDetail[] newArray(int size) {
            return new InspectItemDetail[size];
        }
    };
}