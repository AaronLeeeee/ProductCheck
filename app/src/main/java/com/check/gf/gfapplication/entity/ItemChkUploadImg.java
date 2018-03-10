package com.check.gf.gfapplication.entity;

import okhttp3.RequestBody;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 上传图片信息
 */

public class ItemChkUploadImg {

    private String equipmentNo;
    private String inspectCode;
    private String itemCode;
    private RequestBody uploadImg;


    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getInspectCode() {
        return inspectCode;
    }

    public void setInspectCode(String inspectCode) {
        this.inspectCode = inspectCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public RequestBody getUploadImg() {
        return uploadImg;
    }

    public void setUploadImg(RequestBody uploadImg) {
        this.uploadImg = uploadImg;
    }
}
