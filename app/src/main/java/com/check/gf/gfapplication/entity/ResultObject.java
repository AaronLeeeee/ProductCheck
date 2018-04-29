package com.check.gf.gfapplication.entity;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   :
 */

public class ResultObject<T> {

    private int result;
    private String desc;
    private T data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
