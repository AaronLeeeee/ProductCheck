package com.check.gf.gfapplication.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 班组
 */
public class GroupData implements IPickerViewData {

    /**
     * groupCode : 001
     * groupName : 一期一班
     */

    private String groupCode;
    private String groupName;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    // 显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
    @Override
    public String getPickerViewText() {
        return groupName;
    }
}
