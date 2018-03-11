package com.check.gf.gfapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/11
 * desc   : 检验条目详细信息
 */
public class InspectItemDetail extends ResultObject implements Parcelable {

    /**
     * data : {"itemCode":"001","itemName":"表面不能有破损，摩察系数不能太大","checkResult":0,"checkContent":"检验结果备注","pictures":[{"url":"http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg"},{"url":"http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg"}]}
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
         * itemCode : 001
         * itemName : 表面不能有破损，摩察系数不能太大
         * checkResult : 0
         * checkContent : 检验结果备注
         * pictures : [{"url":"http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg"},{"url":"http://h.hiphotos.baidu.com/image/pic/item/e824b899a9014c0899ee068a067b02087af4f4cc.jpg"}]
         */

        private String itemCode;
        private String itemName;
        private int checkResult;
        private String checkContent;
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

        public String getCheckContent() {
            return checkContent;
        }

        public void setCheckContent(String checkContent) {
            this.checkContent = checkContent;
        }

        public List<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(List<PicturesBean> pictures) {
            this.pictures = pictures;
        }

        public static class PicturesBean {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.itemCode);
            dest.writeString(this.itemName);
            dest.writeInt(this.checkResult);
            dest.writeString(this.checkContent);
            dest.writeList(this.pictures);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.itemCode = in.readString();
            this.itemName = in.readString();
            this.checkResult = in.readInt();
            this.checkContent = in.readString();
            this.pictures = new ArrayList<PicturesBean>();
            in.readList(this.pictures, PicturesBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
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

    public InspectItemDetail() {
    }

    protected InspectItemDetail(Parcel in) {
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<InspectItemDetail> CREATOR = new Parcelable.Creator<InspectItemDetail>() {
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