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

    public PostData() {

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
        private List<String> groupCodes;
        private List<String> groupNames;

        public List<String> getGroupCodes() {
            return groupCodes;
        }

        public void setGroupCodes(List<String> groupCodes) {
            this.groupCodes = groupCodes;
        }

        public List<String> getGroupNames() {
            return groupNames;
        }

        public void setGroupNames(List<String> groupNames) {
            this.groupNames = groupNames;
        }
    }

}
