package com.check.gf.gfapplication.network;


import com.check.gf.gfapplication.entity.ResultObject;
import com.check.gf.gfapplication.entity.TeamGroupResult;
import com.check.gf.gfapplication.entity.User;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * User Api
 *
 * @author nEdAy
 */
public interface UserApi {

    /**
     * 登录
     *
     * @param user : username 帐号 password 密码
     * @return {
     * "result": 0,  //	标志位，0：成功；1： 失败
     * "desc": "登录成功"  //	描述信息
     * }
     */
    @POST("User/Login")
    Observable<ResultObject> login(@Body User user);

    /**
     * 工位班组查询接口
     *
     * @return 工位班组信息
     */
    @POST("User/PostQuery")
    Observable<TeamGroupResult> postQuery();

}
