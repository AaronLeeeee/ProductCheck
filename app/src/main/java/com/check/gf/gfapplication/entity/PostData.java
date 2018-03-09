package com.check.gf.gfapplication.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 工位
 */
public class PostData implements IPickerViewData {

    /**
     * id : 1
     * postCode : 001
     * postName : 一期
     * groups : [{"groupCode":"001","groupName":"一期一班"},{"groupCode":"001","groupName":"一期一班"}]
     */

    private int id;
    private String postCode;
    private String postName;
    private List<GroupData> groups;

    public PostData(int id, String postCode, String postName, List<GroupData> groups) {
        this.id = id;
        this.postCode = postCode;
        this.postName = postName;
        this.groups = groups;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public List<GroupData> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupData> groups) {
        this.groups = groups;
    }

    // 显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
    @Override
    public String getPickerViewText() {
        return postName;
    }
}
