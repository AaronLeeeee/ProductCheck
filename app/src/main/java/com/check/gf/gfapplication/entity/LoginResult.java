package com.check.gf.gfapplication.entity;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 登录接口返回信息
 */
public class LoginResult extends ResultObject {

    /**
     * data : {"realName":"张三"}
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
         * realName : 张三
         */

        private String realName;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }
}