package com.check.gf.gfapplication.entity;

import java.util.List;

/**
 * author : nEdAy
 * time   : 2018/3/9
 * desc   : 工位班组
 */
public class TeamGroupResult extends ResultObject {

    private List<PostData> data;

    public List<PostData> getData() {
        return data;
    }

    public void setData(List<PostData> data) {
        this.data = data;
    }

}
