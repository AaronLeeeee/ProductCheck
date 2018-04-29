package com.check.gf.gfapplication.entity;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 检验条目信息
 */
public class InspectItem {

    /**
     * itemCode : 001
     * materialCode : 001
     * itemName : 表面不能有破损，摩察系数不能太大
     * checkResult : 0
     * checkContent1 : 检验结果备注
     * checkContent2 : 检验结果备注
     * checkContent3 : 检验结果备注
     * checkContent4 : 检验结果备注
     * checkContent5 : 检验结果备注
     */

    private String itemCode;
    private String materialCode;
    private String itemName;
    private int checkResult;
    private String checkContent1;
    private String checkContent2;
    private String checkContent3;
    private String checkContent4;
    private String checkContent5;

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

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }
}