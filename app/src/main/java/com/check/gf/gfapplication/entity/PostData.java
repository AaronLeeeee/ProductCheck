package com.check.gf.gfapplication.entity;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 工位班组
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

    @Override
    public String getPickerViewText() {
        return postName;
    }

    public class GroupData {

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

    }

}