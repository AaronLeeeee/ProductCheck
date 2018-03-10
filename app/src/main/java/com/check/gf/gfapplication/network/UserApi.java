package com.check.gf.gfapplication.network;


import com.check.gf.gfapplication.entity.LoginResult;
import com.check.gf.gfapplication.entity.TeamGroupResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
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
     * @param username 帐号
     * @param password 密码（
     * @return {
     * "result": 0,  //	标志位，0：成功；1： 失败
     * "desc": "登录成功"  //	描述信息
     * }
     */
    @GET("User/Login")
    Observable<LoginResult> login(@Query("username") String username, @Query("password") String password);

    /**
     * 工位班组查询接口
     *
     * @return 工位班组信息
     */
    @GET("User/PostQuery")
    Observable<TeamGroupResult> postQuery();

}
