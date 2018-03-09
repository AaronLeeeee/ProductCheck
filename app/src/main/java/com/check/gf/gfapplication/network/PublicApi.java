package com.check.gf.gfapplication.network;

/**
 * Public Api
 *
 * @author nEdAy
 */
public interface PublicApi {

//    /**
//     * 签到接口
//     *
//     * @param userSignInfo 签到信息
//     * @return 签到反馈信息
//     */
//    @POST("AttendanceService/iflytekservices/Client/AttendanceByiFly")
//    //@Headers("Pragma: no-cache")
//    Observable<ResponseInfo> AttendanceByiFly(@Body UserSignInfo userSignInfo);
//
//
//    /**
//     * 条件查询签到用户信息
//     *
//     * @param options 条件参数
//     * @return 返回的签到用户信息
//     */
//    @Headers({"X-Bmob-Application-Id: e25f8b61aa8bdba8efbf84ee86430111", "X-Bmob-REST-API-Key: e5147a5dca222678589d4739f1a24a90"})
//    @GET("https://api.bmob.cn/1/classes/UserSignInfo")
//    Observable<UserSignInfo> queryUserSignInfo(@QueryMap Map<String, Object> options);
//
//
//    /**
//     * 更新某条签到用户信息
//     *
//     * @param objectId     签到用户信息Id
//     * @param userSignInfo 需要更新的签到用户信息字段内容
//     * @return 回调信息
//     */
//    @Headers({"X-Bmob-Application-Id: e25f8b61aa8bdba8efbf84ee86430111", "X-Bmob-REST-API-Key: e5147a5dca222678589d4739f1a24a90"})
//    @PUT("https://api.bmob.cn/1/classes/UserSignInfo/{objectId}")
//    Observable<BaseObject> updateUserSignInfo(@Path("objectId") String objectId, @Body UserSignInfo userSignInfo);
//
//
//    /**
//     * 发送微信通知 172.16.152.2 夜神网关
//     * http://172.16.152.2:8000/send/张权译/test success
//     *
//     * @param name 微信昵称
//     * @param text 发送内容
//     * @return 回调信息 ok?
//     */
//    //@PUT("http://172.16.152.2:8000/send/{name}/{text}")
//    @PUT("http://10.0.2:8000/send/{name}/{text}")
//    Observable<String> sendMessageToWx(@Path("name") String name, @Path("text") String text);
}
