package com.check.gf.gfapplication.network;

import com.check.gf.gfapplication.entity.KeepAliveObject;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Public Api
 *
 * @author nEdAy
 */
public interface PublicApi {

    /**
     * 检查接口
     */
    @GET("http://47.94.46.239/SMS/front/public/js/keepalive.json")
    Observable<KeepAliveObject> keepalive();

}
