package com.check.gf.gfapplication.network;

import java.util.Map;

import com.wjj.easy.easyandroid.http.Http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by wqd on 2018/3/3.
 */

public class OkHttpUtils {

    private static OkHttpClient mInstance;

    public static void initClient(OkHttpClient okHttpClient) {
        mInstance = okHttpClient;
    }

    /**
     * @param url      请求地址
     * @param callback 请求回调
     * @Description GET请求
     */
//    public static void getSync(String url, HttpCallback callback) {
//        Request request = Request.Builder(HttpMethodType.GET, url, null, null);
//        OkHttpRequest.doExecute(request, callback);
//    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description GET请求
     */
//    public static void getSync(String url, Map<String, String> params, Http.HttpCallback callback) {
//        if (params != null && !params.isEmpty()) {
//            url = OkHttpRequest.appendGetParams(url, params);
//        }
//        Request request = OkHttpRequest.builderRequest(HttpMethodType.GET, url, null, null);
//        OkHttpRequest.doExecute(request, callback);
//    }

    /**
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 请求回调
     * @Description POST请求
     */
//    public static void postSync(String url, Map<String, String> params, Http.HttpCallback callback) {
//        Request request = OkHttpRequest.builderRequest(HttpMethodType.POST, url, params, null);
//        OkHttpRequest.doExecute(request, callback);
//    }


}
