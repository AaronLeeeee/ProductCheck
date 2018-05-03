package com.check.gf.gfapplication.entity;

/**
 * author : nEdAy
 * time   : 2018/5/3
 * desc   : 结束检查 返回时间信息
 * data : {"finishCheckTime":"2018-03-09 11:30"}
 */
public class FinishCheckResult {

    /**
     * finishCheckTime : 2018-03-09 11:30
     */

    private String finishCheckTime;


    public String getFinishCheckTime() {
        return finishCheckTime;
    }

    public void setFinishCheckTime(String finishCheckTime) {
        this.finishCheckTime = finishCheckTime;
    }
}