package com.check.gf.gfapplication.model;

import java.io.Serializable;

import org.litepal.crud.DataSupport;

/**
 * 来料检 model
 * Created by wqd on 2018/1/2.
 */

public class IncomeCheck extends DataSupport implements Serializable {
    public int id;
    public String client; //客户
    public String checkSingleId; //String 检验单号
    public String purchaseOrderId; //String 采购订单号
    public String supplier; // String 供应商
    public String materialId; // String   物料号
    public String materialName; //String 物料名称
    public int incomeCount; //int 来料数量
    public String checkDate; //String 检验日期 格式”yyyy-MM-dd”
    public int needCheckNum; //int  待检测数量
    public int totalCheckNum; //int 检测数量
    public int checkState; //int 检测状态  0-未开始检验 1-检验中  2-检验完成
    public String inspector; //String 检查员
    public String startTime; //String 检查开始时间
    public String endTime; //String 检查结束时间


    public String getClient() {
        return client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCheckSingleId() {
        return checkSingleId;
    }

    public void setCheckSingleId(String checkSingleId) {
        this.checkSingleId = checkSingleId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public int getIncomeCount() {
        return incomeCount;
    }

    public void setIncomeCount(int incomeCount) {
        this.incomeCount = incomeCount;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public int getNeedCheckNum() {
        return needCheckNum;
    }

    public void setNeedCheckNum(int needCheckNum) {
        this.needCheckNum = needCheckNum;
    }

    public int getTotalCheckNum() {
        return totalCheckNum;
    }

    public void setTotalCheckNum(int totalCheckNum) {
        this.totalCheckNum = totalCheckNum;
    }

    public int getCheckState() {
        return checkState;
    }

    public void setCheckState(int checkState) {
        this.checkState = checkState;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
