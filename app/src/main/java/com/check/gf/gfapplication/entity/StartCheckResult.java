package com.check.gf.gfapplication.entity;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 开始检查 返回时间信息
 */
public class StartCheckResult extends ResultObject {
    /**
     * data : {"startCheckTime":"2018-03-09 11:30"}
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
         * startCheckTime : 2018-03-09 11:30
         */

        private String startCheckTime;

        public String getStartCheckTime() {
            return startCheckTime;
        }

        public void setStartCheckTime(String startCheckTime) {
            this.startCheckTime = startCheckTime;
        }
    }
}